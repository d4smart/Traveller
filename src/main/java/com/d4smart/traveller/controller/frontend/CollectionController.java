package com.d4smart.traveller.controller.frontend;

import com.d4smart.traveller.common.Const;
import com.d4smart.traveller.common.PageInfo;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.pojo.User;
import com.d4smart.traveller.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by d4smart on 2018/4/11 16:59
 */
@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ServerResponse add(Integer guideId, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return collectionService.add(user.getId(), guideId);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ServerResponse<PageInfo> list(HttpSession session, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return collectionService.list(user.getId(), pageNum, pageSize);
    }

    @RequestMapping(value = "/isCollected", method = RequestMethod.GET)
    public ServerResponse isCollected(Integer guideId, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return collectionService.isCollected(user.getId(), guideId);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ServerResponse delete(Integer guideId, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return collectionService.delete(user.getId(), guideId);
    }
}
