package com.d4smart.traveller.controller.frontend;

import com.d4smart.traveller.common.Const;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.pojo.Guide;
import com.d4smart.traveller.pojo.User;
import com.d4smart.traveller.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by d4smart on 2018/4/2 14:08
 */
@RestController
@RequestMapping("/guide")
public class GuideController {

    @Autowired
    private GuideService guideService;

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public ServerResponse publish(Guide guide, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        guide.setAuthorId(user.getId());
        return guideService.publish(guide);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ServerResponse edit(Guide guide, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return guideService.edit(guide, user.getId());
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ServerResponse search(String title, String places, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return guideService.search(title, places, pageNum, pageSize);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ServerResponse list(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return guideService.list(user.getId(), pageNum, pageSize);
    }

    // todo 根据用户关注生成的攻略动态
    @RequestMapping(value = "/dynamic", method = RequestMethod.GET)
    public ServerResponse dynamic(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return null;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ServerResponse get(Integer id) {
        return guideService.get(id);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ServerResponse delete(Integer id, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return guideService.delete(id, user.getId());
    }
}
