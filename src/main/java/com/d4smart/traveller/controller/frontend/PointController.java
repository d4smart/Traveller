package com.d4smart.traveller.controller.frontend;

import com.d4smart.traveller.common.Const;
import com.d4smart.traveller.common.PageInfo;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.pojo.Point;
import com.d4smart.traveller.pojo.User;
import com.d4smart.traveller.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by d4smart on 2018/5/11 11:02
 */
@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointService pointService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ServerResponse add(Point point, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return pointService.add(point, user.getId());
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ServerResponse edit(Point point, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return pointService.edit(point, user.getId());
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ServerResponse<PageInfo> list(Integer trailId, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return pointService.list(trailId, pageNum, pageSize);
    }
}
