package com.garylee.framework.servlet;

import com.garylee.framework.handler.MappingHandler;
import com.garylee.framework.ioc.BeanFactoryImpl;
import com.garylee.framework.structure.ClassFactory;
import com.garylee.framework.structure.HtmlFactory;
import com.garylee.framework.structure.MethodMap;
import com.garylee.framework.utils.Config;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;
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
        new BeanFactoryImpl().init();
        System.out.println("bean初始化完毕!");

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
        else if(req.getRequestURI().equals("/upload")){
            String filename = null;
            //测试文件上传
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            //设置上传文件的大小限制为1M
            factory.setSizeThreshold(1024 * 1024);
            List items = null;
            try {
                items = upload.parseRequest(req);
            }catch (Exception e){
                e.printStackTrace();
            }
            Iterator iter = items.iterator();
            while(iter.hasNext()){
                FileItem item = (FileItem) iter.next();
                if(!item.isFormField()){
                    filename = System.currentTimeMillis()+".jpg";
                    //图片路径(存放在target下，原src没同步，可自己添加)
                    String photoFolder = Config.targetPath+"\\static";
                    File file = new File(photoFolder,filename);
                    file.getParentFile().mkdirs();
                    InputStream is = item.getInputStream();
                    //复制文件
                    FileOutputStream fos = new FileOutputStream(file);
                    byte b[] = new byte[1024 * 1024];
                    int length = 0;
                    while(-1 != (length = is.read(b))){
                        fos.write(b,0,length);
                    }
                    fos.close();
                } else {
                    System.out.println(item.getFieldName());
                    String value = item.getString();
                    value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
                    System.out.println(value);
                }
            }
            String html = "<img width='200' height='150' src='%s'/>";
            resp.setContentType("text/html");
            resp.getWriter().format(html,filename);
        } else {
            String uri = req.getRequestURI();
            System.out.println("用户请求:" + uri);
            System.out.println("方法:" + req.getMethod());
            if (methodsMap.containsKey(uri)) {
                MappingHandler.handler(req, resp, uri, classMap, methodsMap, htmlMap);
            }
        }
    }


}
