package com.garylee.framework.test;

import com.garylee.framework.ioc.Bean;
import com.garylee.framework.ioc.BeanFactory;
import com.garylee.framework.ioc.BeanFactoryImpl;

import java.io.File;
import java.net.URL;

/**
 * Created by GaryLee on 2019-03-19 18:04.
 */
public class IocTest {
//    BeanFactoryImpl beanFactory = new BeanFactoryImpl();
    URL url = Thread.currentThread().getContextClassLoader().getResource("");
    public void init(){
        System.out.println("1:"+url.getFile());
        System.out.println("2:"+new File(url.getFile()).getAbsolutePath());
        scan(url.getFile());
    }
    //扫描path路径下的文件
    public void scan(String path){
        File file = new File(path);
        File[] files = file.listFiles();
        for(File f:files){
            if(f.isDirectory()){
                scan(f.getAbsolutePath());
            }else if(f.getName().endsWith(".class")){
                //将比如User.class转换为User
                String fileName = f.getName().substring(0,f.getName().length()-6);
                String targetPath = new File(url.getFile()).getAbsolutePath();
                //由绝对路径转为类似com.garylee.framework.test.IocTest
                //方便Class查找
                String className = f.getAbsolutePath()
                        .substring(0,f.getAbsolutePath().lastIndexOf(".class"))
                        .replace(targetPath,"")
                        .replace("\\",".")
                        .substring(1);

                Bean bean = new Bean();
                bean.setName(fileName);
                bean.setClassName(className);

                //将bean存放到map中(未实例化)
                BeanFactoryImpl.beanMap.put(fileName,bean);
                System.out.println("bean<"+fileName+">生成!");
            }

        }
    }
    public static void main(String[] args) {
        new IocTest().init();
    }
}
