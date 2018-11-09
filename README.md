# lee-framework
一款原生的SpringMVC框架
### 介绍
这里用来完善之前写过的[springmvc_framework](https://github.com/GaryLeeeee/springmvc_framework)，并使用了内嵌tomcat。
###  特点
* 使用注解(约定大于配置),保证代码规范
* 反射实现
* 内嵌tomcat
### 功能
* 注解实现(@Controller,@ResponseBody,@RequestMapping)
* 路由跳转，当没有@ResponseBody，则默认return的是String，并解析为templates
* 反射实现方法参数注入(invoke方法)
* 判断并返回静态文件，统一放在static包
### 代码演示
```Java
/**
 * Created by GaryLee on 2018-10-25 23:12.
 * @Controller 标记为控制器类，将会扫描里面的方法
 */
@Controller
public class HelloController {
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
```
### 注意
* 调用方法在jdk8之前获取的只是arg0,arg1...，若需获取对应的形参名，需在Java Compiler修改编译版本为1.8，并且在 Additional command line parameters: 后面填上 -parameters（参考[CSDN教程](https://blog.csdn.net/baidu_32492845/article/details/79712141)）
