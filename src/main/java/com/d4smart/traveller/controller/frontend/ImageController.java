package com.d4smart.traveller.controller.frontend;

import com.d4smart.traveller.common.Const;
import com.d4smart.traveller.common.ServerResponse;
import com.d4smart.traveller.pojo.Image;
import com.d4smart.traveller.pojo.User;
import com.d4smart.traveller.service.FileService;
import com.d4smart.traveller.service.ImageService;
import com.d4smart.traveller.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Calendar;

/**
 * Created by d4smart on 2018/3/29 16:47
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ServerResponse upload(@RequestParam(value = "image") MultipartFile file, HttpSession session) {
        Calendar calendar = Calendar.getInstance();
        String frontLocation = PropertiesUtil.getProperty("front.location");
        String filename = file.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf(".") + 1);
        String path = "/image/" + calendar.get(Calendar.YEAR)
                + "/" + String.format("%02d", calendar.get(Calendar.MONTH) + 1)
                + "/" + calendar.getTimeInMillis() + "." + ext;

        ServerResponse serverResponse = fileService.upload(file, frontLocation + path);
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }

        User user = (User) session.getAttribute(Const.LOGIN_USER);
        Image image = new Image();
        image.setUserId(user.getId());
        image.setPath(path);

        return imageService.create(image);
    }
}
