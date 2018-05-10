package com.d4smart.traveller.controller.frontend;

import com.d4smart.traveller.common.Const;
import com.d4smart.traveller.common.PageInfo;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.pojo.User;
import com.d4smart.traveller.service.UserService;
import com.google.code.kaptcha.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by d4smart on 2018/3/30 9:25
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ServerResponse register(User user, String captcha, HttpSession session) {
        String expected = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (expected == null || !expected.equals(captcha)) {
            return ServerResponse.createByErrorMessage("验证码错误");
        }

        return userService.register(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, String captcha, HttpSession session) {
        String expected = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (expected == null || !expected.equals(captcha)) {
            return ServerResponse.createByErrorMessage("验证码错误");
        }

        ServerResponse<User> serverResponse = userService.login(username, password);
        if (serverResponse.isSuccess()) {
            session.setAttribute(Const.LOGIN_USER, serverResponse.getData());
        }
        return serverResponse;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ServerResponse<User> info(HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return ServerResponse.createBySuccess(user);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ServerResponse get(Integer id) {
        return userService.get(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ServerResponse<User> update(User user, HttpSession session) {
        User login = (User) session.getAttribute(Const.LOGIN_USER);
        user.setId(login.getId());

        ServerResponse<User> serverResponse = userService.update(user);
        if (serverResponse.isSuccess()) {
            session.setAttribute(Const.LOGIN_USER, serverResponse.getData());
        }

        return serverResponse;
    }

    @RequestMapping(value = "/checkValid", method = RequestMethod.GET)
    public ServerResponse checkValid(String type, String value) {
        return userService.checkValid(type, value);
    }

    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public ServerResponse follow(@RequestParam(value = "id", required = false) Integer followId, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return userService.follow(user.getId(), followId);
    }

    @RequestMapping(value = "/unfollow", method = RequestMethod.POST)
    public ServerResponse unfollow(@RequestParam(value = "id", required = false) Integer followId, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return userService.unfollow(user.getId(), followId);
    }

    @RequestMapping(value = "/isFollowing", method = RequestMethod.GET)
    public ServerResponse isFollowing(@RequestParam(value = "id", required = false) Integer followId, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return userService.isFollowing(user.getId(), followId);
    }

    @RequestMapping(value = "/follower", method = RequestMethod.GET)
    public ServerResponse<PageInfo> follower(HttpSession session, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return userService.follows(null, user.getId(), pageNum, pageSize);
    }

    @RequestMapping(value = "/following", method = RequestMethod.GET)
    public ServerResponse<PageInfo> following(HttpSession session, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return userService.follows(user.getId(), null, pageNum, pageSize);
    }

    @RequestMapping(value = "/dynamics", method = RequestMethod.GET)
    public ServerResponse<PageInfo> dynamics(HttpSession session, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return userService.dynamics(user.getId(), pageNum, pageSize);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ServerResponse logout(HttpSession session) {
        session.removeAttribute(Const.LOGIN_USER);
        return ServerResponse.createBySuccessMessage("退出登陆成功");
    }
}
