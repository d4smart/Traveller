package com.d4smart.traveller.controller.frontend;

import com.d4smart.traveller.common.Const;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.pojo.User;
import com.d4smart.traveller.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public ServerResponse register(User user) {
        return userService.register(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> serverResponse = userService.login(username, password);
        if (serverResponse.isSuccess()) {
            session.setAttribute(Const.LOGIN_USER, serverResponse.getData());
        }
        return serverResponse;
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return ServerResponse.createBySuccess(user);
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

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ServerResponse logout(HttpSession session) {
        session.removeAttribute(Const.LOGIN_USER);
        return ServerResponse.createBySuccessMessage("退出登陆成功");
    }
}
