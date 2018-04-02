package com.d4smart.traveller.controller.frontend;

import com.d4smart.traveller.common.Const;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.pojo.User;
import com.d4smart.traveller.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * Created by d4smart on 2018/3/29 16:47
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ServerResponse upload(@RequestParam(value = "image") MultipartFile file, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return imageService.upload(file, user.getId());
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ServerResponse edit(Integer id, @RequestParam(value = "image") MultipartFile file, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return imageService.edit(id, file, user.getId());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ServerResponse getAll(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return imageService.getAll(user.getId(), pageNum, pageSize);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ServerResponse delete(Integer id, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        return imageService.delete(id, user.getId());
    }
}
