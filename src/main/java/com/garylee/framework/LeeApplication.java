package com.garylee.framework;

import com.garylee.framework.servlet.DispatcherServlet;
import com.garylee.framework.servlet.StaticServlet;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

/**
 * Created by GaryLee on 2018-10-23 08:34.
 */
public class LeeApplication {
    public void init(){

    }
    public static void main(String[] args) throws ServletException, LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(9999);
        //这个暂时没啥用，反正是自己处理servlet
        String path = System.getProperty("user.dir")+"\\src\\main\\resources\\templates";
        tomcat.setBaseDir(path);
        tomcat.getHost().setAutoDeploy(false);
        tomcat.getHost().setAppBase(path);

        tomcat.addWebapp("/jsp",path);

        StandardContext context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());
        tomcat.getHost().addChild(context);

        tomcat.addServlet("", "homeServlet", new DispatcherServlet());
        context.addServletMappingDecoded("/*", "homeServlet");
        tomcat.addServlet("", "staticServlet", new StaticServlet());
        context.addServletMappingDecoded("/static/*", "staticServlet");

        tomcat.start();
        tomcat.getServer().await();
    }
}
