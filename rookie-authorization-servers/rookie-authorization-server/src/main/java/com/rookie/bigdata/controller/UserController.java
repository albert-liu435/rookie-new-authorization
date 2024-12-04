//package com.rookie.bigdata.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.security.Principal;
//import java.util.Collections;
//import java.util.Map;
//
///**
// * @Class UserController
// * @Description
// * @Author rookie
// * @Date 2024/8/9 15:26
// * @Version 1.0
// */
//@Controller
//@RequiredArgsConstructor
//public class UserController {
//
//    @ResponseBody
//    @GetMapping("/user")
//    public Map<String, Object> user(Principal principal) {
//        if (!(principal instanceof JwtAuthenticationToken token)) {
//            return Collections.emptyMap();
//        }
//        return token.getToken().getClaims();
//    }
//
//}
