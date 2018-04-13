package com.d4smart.traveller.controller.frontend;

import com.d4smart.traveller.common.Const;
import com.d4smart.traveller.common.PageInfo;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.pojo.Comment;
import com.d4smart.traveller.pojo.User;
import com.d4smart.traveller.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by d4smart on 2018/4/11 14:06
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ServerResponse add(Integer guideId, String content, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return commentService.add(user.getId(), guideId, content);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ServerResponse edit(Comment comment, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return commentService.edit(comment, user.getId());
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ServerResponse<Comment> get(Integer id) {
        return commentService.get(id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ServerResponse<PageInfo> list(Integer userId, Integer guideId, HttpSession session,
                                         @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return commentService.list(userId, guideId, user.getId(), pageNum, pageSize);
    }

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public ServerResponse like(Integer id, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return commentService.like(id, user.getId());
    }

    @RequestMapping(value = "/unlike", method = RequestMethod.POST)
    public ServerResponse unlike(Integer id, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return commentService.unlike(id, user.getId());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ServerResponse delete(Integer id, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return commentService.delete(id, user.getId());
    }
}
