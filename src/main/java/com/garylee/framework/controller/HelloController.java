package com.garylee.framework.controller;

import com.garylee.framework.annotation.Controller;
import com.garylee.framework.annotation.RequestMapping;
import com.garylee.framework.annotation.ResponseBody;

/**
 * Created by GaryLee on 2018-10-25 23:12.
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
    @RequestMapping("/getName")
    @ResponseBody
    public String getName(){
        return "GaryLee";
    }
    @RequestMapping("/count")
    @ResponseBody
    public int count(int a,int b){
        System.out.println("a+b="+a+b);
        return a+b;
    }
    @RequestMapping("/add")
    @ResponseBody
    public String add(String a,String b){
        System.out.println("a+b="+a+b);
        return a+b;
    }
    @RequestMapping("/login")
    @ResponseBody
    public String login(String username,String password){
        if(username.equals("root")&&password.equals("admin"))
            return "success";
        else
            return "fail";
    }
    @RequestMapping(value = "/post",method = "post")
    @ResponseBody
    public String post(String username,String password){
        if(username.equals("root")&&password.equals("admin"))
            return "success";
        else
            return "fail";
    }
}
