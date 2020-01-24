package com.txx.security.controller;

import com.txx.security.common.ResponseData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author labvi
 * @version 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class AdminTestController {

    @Value("${server.port}")
    private  String port;
    @GetMapping("/home")
    @ResponseBody
    public ResponseData productInfo(HttpServletRequest request){
        ServletContext servletContext = request.getSession().getServletContext();
        WebApplicationContext context = (WebApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        RedisConnectionFactory bean = context.getBean(RedisConnectionFactory.class);
        System.out.println(bean);
        return ResponseData.ok("admin home page" + port);
    }
}
