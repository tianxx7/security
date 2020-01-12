package com.txx.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author labvi
 * @version 1.0.0
 */
@Controller
@RequestMapping("/product")
public class ProductTestController {
    @GetMapping("/info")
    @ResponseBody
    public String productInfo(){

        return "some product info";
    }
}
