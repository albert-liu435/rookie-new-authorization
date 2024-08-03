package com.rookie.bigdata.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author rookie
 * @Description
 * @Date 2024/7/31 21:46
 * @Version 1.0
 */

@RestController
//@RequestMapping("/a")
public class HelloController {


    @RequestMapping("/hello")
    public String Hello(){
        return "a";
    }

}
