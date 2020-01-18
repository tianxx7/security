package com.txx.security.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 获取图形验证码
 */
@RestController
public class CaptchaController {
    @Autowired
    private Producer captchaProducer;
    @GetMapping("/captcha.jpg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        String capText = captchaProducer.createText();
        //将验证码设置到session
        request.getSession().setAttribute("captcha",capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(bi,"jpg",outputStream);
        try {
            outputStream.flush();
        } finally {
            outputStream.close();
        }
    }
}
