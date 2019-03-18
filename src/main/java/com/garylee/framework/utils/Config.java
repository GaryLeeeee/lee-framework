package com.garylee.framework.utils;

import java.io.File;

/**
 * Created by GaryLee on 2018-08-16 19:29.
 */
public class Config {
//    public static String projectPath = System.getProperty("user.dir");
//    public static String staticPath = projectPath + "\\src\\main\\resources\\static";
//    public static String templatesPath = projectPath + "\\src\\main\\resources\\templates";

    public static String targetPath = new File(Thread.currentThread().getContextClassLoader().getResource("").getFile()).getAbsolutePath();
}
