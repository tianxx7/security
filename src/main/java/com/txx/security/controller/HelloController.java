package com.txx.security.controller;

import com.txx.security.common.ResponseData;
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
    public ResponseData hello(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseData.ok("hello security,current user is "+principal);
    }


}
