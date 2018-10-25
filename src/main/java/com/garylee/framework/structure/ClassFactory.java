package com.garylee.framework.structure;

import com.garylee.framework.annotation.Controller;
import com.garylee.framework.annotation.RequestMapping;
import com.garylee.framework.annotation.ResponseBody;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by GaryLee on 2018-10-25 22:15.
 */
public class ClassFactory {
    private static Set<Class> classSet = new HashSet<>();//存放所有类
    private static Map<String,MethodMap> methodMap;//存放url和对应的method
    private static Map<String,Class> classMap;//存放url和对应的class
    private static String projectPath = "com\\garylee\\framework";//包路径(修改为src)
    private static String path = System.getProperty("user.dir") + "\\src\\main\\java\\com\\garylee\\framework";
    public static void scan(){
        initSet(path);//扫描所有类并存放到set中
        methodMap = new HashMap<>();
        classMap = new HashMap<>();
        for(Class clazz:classSet){
//            System.out.println(clazz);//输出类
            if(clazz.isAnnotationPresent(Controller.class)){
                Method[] methods = clazz.getDeclaredMethods();
                for(Method method:methods){
                    if(method.isAnnotationPresent(RequestMapping.class)){
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        boolean isBody = false;
                        if(method.isAnnotationPresent(ResponseBody.class)){
                            isBody = true;
                        }
                        //methodMap存放当前URL和对应
                        MethodMap method1 = new MethodMap(requestMapping.value(),requestMapping.method(),isBody,method);
                        methodMap.put(requestMapping.value(),method1);
                        System.out.println(requestMapping.value()+"初始化成功!");

                        classMap.put(requestMapping.value(),clazz);
                    }
                }
            }
        }
    }
    private static void initSet(String path){
        File[] files = new File(path).listFiles();
        for(File file:files){
            //如果是文件夹，则递归调用(即进行子文件夹)
            if(file.isDirectory())
                initSet(file.getAbsolutePath());
            if(file.getName().contains(".java")){
                String paths = file.getAbsolutePath();
                //文件名(包括package,如com.garylee.framework.annotation.Controller)
                String className = paths.substring(paths.indexOf(projectPath)).replace(".java","").replace(File.separator,".");
//                System.out.println(className);//Class.forName的值
//                System.out.println(Class.forName(className));
                try {
                    classSet.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public static Set<Class> getClassSet() {
        return classSet;
    }

    public static void setClassSet(Set<Class> classSet) {
        ClassFactory.classSet = classSet;
    }

    public static Map<String, MethodMap> getMethodMap() {
        return methodMap;
    }

    public static void setMethodMap(Map<String, MethodMap> methodMap) {
        ClassFactory.methodMap = methodMap;
    }

    public static Map<String, Class> getClassMap() {
        return classMap;
    }

    public static void setClassMap(Map<String, Class> classMap) {
        ClassFactory.classMap = classMap;
    }
}
