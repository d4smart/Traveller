package com.d4smart.traveller.controller.frontend;

import com.d4smart.traveller.common.Const;
import com.d4smart.traveller.common.PageInfo;
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
        return guideService.publish(guide, user);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ServerResponse edit(Guide guide, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return guideService.edit(guide, user.getId());
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ServerResponse<PageInfo> search(String title, String places, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return guideService.search(title, places, true, pageNum, pageSize);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ServerResponse<PageInfo> list(HttpSession session, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return guideService.list(user.getId(), true, pageNum, pageSize);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ServerResponse<Guide> get(Integer id) {
        return guideService.get(id);
    }

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public ServerResponse like(Integer id, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return guideService.like(id, user.getId());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ServerResponse delete(Integer id, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return guideService.delete(id, user.getId());
    }
}
