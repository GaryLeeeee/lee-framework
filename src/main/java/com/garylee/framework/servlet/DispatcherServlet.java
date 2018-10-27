package com.garylee.framework.servlet;

import com.garylee.framework.handler.MappingHandler;
import com.garylee.framework.structure.ClassFactory;
import com.garylee.framework.structure.HtmlFactory;
import com.garylee.framework.structure.MethodMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by GaryLee on 2018-10-23 08:28.
 */
public class DispatcherServlet extends HttpServlet {
    private static Map<String,Class> classMap = null;
    private static Map<String,MethodMap> methodsMap = null;
    private static Map<String,String> htmlMap = null;
    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("Servlet初始化ing...");
        ClassFactory.scan();
        classMap = ClassFactory.getClassMap();
        methodsMap = ClassFactory.getMethodMap();
        htmlMap = HtmlFactory.getHtml();
        System.out.println("Servlet初始化完成!");
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.service(req, resp);//加上这句会报405错误
//        System.out.println("接受数据");
        if(req.getRequestURI().contains("."))
            req.getRequestDispatcher("/static"+req.getRequestURI()).forward(req,resp);
        else {
            String uri = req.getRequestURI();
            System.out.println("用户请求:" + uri);
            System.out.println("方法:" + req.getMethod());
            if (methodsMap.containsKey(uri)) {
                MappingHandler.handler(req, resp, uri, classMap, methodsMap, htmlMap);
            }
        }
    }


}
