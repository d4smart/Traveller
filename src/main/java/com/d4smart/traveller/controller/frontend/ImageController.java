package com.d4smart.traveller.controller.frontend;

import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by d4smart on 2018/3/29 16:47
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ServerResponse upload(@RequestParam(value = "image") MultipartFile file, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("image");
        String targetFileName = fileService.upload(file, path);

        return ServerResponse.createBySuccess(targetFileName);
    }
}
