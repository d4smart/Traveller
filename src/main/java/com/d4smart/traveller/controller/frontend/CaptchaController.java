package com.d4smart.traveller.controller.frontend;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by d4smart on 2018/4/16 15:52
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private Producer captchaProducer;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ModelAndView get(HttpSession session, HttpServletResponse response) throws IOException {
        // 设置响应头
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        // 生成验证码并设置session和图片缓存
        String code = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, code);
        BufferedImage image = captchaProducer.createImage(code);

        try (ServletOutputStream out = response.getOutputStream()) {
            ImageIO.write(image, "jpg", out);
            out.flush();
        } catch (IOException e) {
            // ignore?
        }

        return null;
    }
}
