package com.txx.security.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author labvi
 * @version 1.0.0
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "hello security,current user is "+principal;
    }


}
