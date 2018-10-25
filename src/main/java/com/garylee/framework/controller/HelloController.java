package com.garylee.framework.controller;

import com.garylee.framework.annotation.Controller;
import com.garylee.framework.annotation.RequestMapping;

/**
 * Created by GaryLee on 2018-10-25 23:12.
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
