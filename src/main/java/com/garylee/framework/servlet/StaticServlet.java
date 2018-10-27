package com.garylee.framework.servlet;

import com.garylee.framework.structure.StaticFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by GaryLee on 2018-10-27 12:33.
 */
public class StaticServlet extends HttpServlet{
    @Override
    public void init() throws ServletException {
        super.init();

    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.service(req, resp);
        String fileName = req.getRequestURI().replace("/static","");
        System.out.println("用户请求静态资源:"+fileName);
        File file = StaticFactory.parseStatic(fileName);
        if (file.exists()){
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
//            resp.getWriter().print(bytes);
            while ((fileInputStream.read(bytes)) != -1) {
                resp.getOutputStream().write(bytes);
            }
        }else {
            System.err.println("静态资源"+fileName+"不存在!");
        }
    }
}
