package com.horen.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author AFZ
 * @Date 2020/9/23 13:48
 **/
@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test01")
    public String test01() {
        return "SUCCESS";
    }

}
