package com.garylee.framework.handler;

import com.garylee.framework.servlet.DispatcherServlet;
import com.garylee.framework.structure.MethodMap;
import com.garylee.framework.structure.StaticFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by GaryLee on 2018-10-23 10:11.
 */
public class MappingHandler {
    public static void handler(HttpServletRequest req, HttpServletResponse resp,String uri,
                               Map<String,Class> classMap,Map<String,MethodMap> methodsMap,Map<String,String> htmlMap){
        try {
            MethodMap methodMap = methodsMap.get(uri);
            Class clazz = classMap.get(uri);
            Object object = clazz.newInstance();
            Map<String, String[]> map = req.getParameterMap();
            String fileName = methodMap.getMethod().invoke(object).toString();
            if (!methodMap.isBody()) {
                if (htmlMap.containsKey(fileName)){
                    resp.setContentType("text/html; charset=UTF-8");//乱码加上这句
                    resp.getWriter().write(htmlMap.get(fileName));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
