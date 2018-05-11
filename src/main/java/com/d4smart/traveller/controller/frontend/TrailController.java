package com.d4smart.traveller.controller.frontend;

import com.d4smart.traveller.common.Const;
import com.d4smart.traveller.common.PageInfo;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.pojo.Trail;
import com.d4smart.traveller.pojo.User;
import com.d4smart.traveller.service.TrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by d4smart on 2018/5/11 9:54
 */
@RestController
@RequestMapping("/trail")
public class TrailController {

    @Autowired
    private TrailService trailService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ServerResponse add(Trail trail, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        trail.setUserId(user.getId());
        return trailService.add(trail);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ServerResponse edit(Trail trail, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return trailService.edit(trail, user.getId());
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ServerResponse<PageInfo> list(Integer userId, HttpSession session, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        if (userId == null) {
            User user = (User) session.getAttribute(Const.LOGIN_USER);
            userId = user.getId();
        }

        return trailService.list(userId, pageNum, pageSize);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ServerResponse list(Integer id) {
        return trailService.get(id);
    }
}
