package com.garylee.framework.controller;

import com.garylee.framework.annotation.Autowired;
import com.garylee.framework.annotation.Controller;
import com.garylee.framework.annotation.RequestMapping;
import com.garylee.framework.annotation.ResponseBody;
import com.garylee.framework.ioc.Property;

/**
 * Created by GaryLee on 2018-10-25 23:12.
 * @Controller 标记为控制器类，将会扫描里面的方法
 */
@Controller
public class HelloController {
    @Autowired
    Property property;
    @RequestMapping("/")
    public String index(){
        return "hello";
    }
    /**
     * @RequestMapping 路由注解,代表该value的路径请求由该函数处理
     * @return 返回hello.html的内容，对应的文件存放在resources/templates中
     */
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
    /**
     * @ResponseBody 标记为ajax接口,可返回各种数据类型
     * @return 此函数返回的是字符串，并非html文件内容
     */
    @RequestMapping("/getName")
    @ResponseBody
    public String getName(){
        return "GaryLee";
    }
    /**
     * @param a 参数，可通过get或post方法传递并调用
     * @param b 同上
     * 示例：
     *  127.0.0.1/count?a=3&b=2
     *  浏览器将会返回5
     */
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
    /**
     * @method 请求的方法,默认为get
     */
    @RequestMapping(value = "/post",method = "post")
    @ResponseBody
    public String post(String username,String password){
        if(username.equals("root")&&password.equals("admin"))
            return "success";
        else
            return "fail";
    }
}
