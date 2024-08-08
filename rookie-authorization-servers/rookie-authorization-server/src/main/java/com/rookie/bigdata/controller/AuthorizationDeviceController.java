package com.rookie.bigdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Class AuthorizationDeviceController
 * @Description
 * @Author rookie
 * @Date 2024/8/8 17:06
 * @Version 1.0
 */
@Controller
public class AuthorizationDeviceController {
//    @Controller
//    public class DeviceController {

        @GetMapping("/activate")
        public String activate(@RequestParam(value = "user_code", required = false) String userCode) {
            if (userCode != null) {
                return "redirect:/oauth2/device_verification?user_code=" + userCode;
            }
            return "device-activate";
        }

        @GetMapping("/activated")
        public String activated() {
            return "device-activated";
        }

        @GetMapping(value = "/", params = "success")
        public String success() {
            return "device-activated";
        }

    }
