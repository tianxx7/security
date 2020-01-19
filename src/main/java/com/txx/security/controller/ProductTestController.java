package com.txx.security.controller;

import com.txx.security.common.ResponseData;
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
    public ResponseData productInfo(){
        return ResponseData.ok("some product info");
    }
}
