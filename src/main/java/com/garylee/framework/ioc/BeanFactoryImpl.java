package com.garylee.framework.ioc;

import com.garylee.framework.utils.Config;
import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GaryLee on 2019-03-19 00:13.
 */
public class BeanFactoryImpl implements BeanFactory{
    //存放对象实例
    private static Map<String,Object> instanceMap = new HashMap<>();
    //存放bean对象
    public static Map<String,Bean> beanMap = new HashMap<>();

    @Override
    public Object getBean(String name) {
        //获取对象实例
        Object bean = instanceMap.get(name);
        //        System.out.println("bean:"+bean);
        //        bean:null         第一次请求为null
        //        bean:User{id=2, name='teemo'}        第二次开始请求返回固定
        //如果对象为null，即还没有创建实例
        if(bean==null) {
            bean = createBean(beanMap.get(name));
        }
        return bean;
    }

    public Object createBean(Bean bean){
        Class clazz = null;
        try {
            clazz =Class.forName(bean.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("找不到类:"+bean.getClassName());
        }

        //开始初始化对象
        Object object = null;
        try {
            object = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //传入参数
        if(bean.getProperties() != null){
            Map<String,String> paramsMap = new HashMap<>();
            for(Property property:bean.getProperties()){
                String name = property.getName();
                String value = property.getValue();
                paramsMap.put(name,value);
            }
            try {
                //属性注入
                BeanUtils.populate(object,paramsMap);
                //放入实例对象map
                instanceMap.put(bean.getName(),object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return object;
    }

    public void init(){
        //扫描并加载所有类为bean
        scan(Config.targetPath);
    }

    public void scan(String path){
        File file = new File(path);
        File[] files = file.listFiles();
        for(File f:files){
            if(f.isDirectory()){
                scan(f.getAbsolutePath());
            }else if(f.getName().endsWith(".class")){
                //将比如User.class转换为User
                String fileName = f.getName().substring(0,f.getName().length()-6);
                String targetPath = new File(Config.targetPath).getAbsolutePath();
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
                beanMap.put(fileName,bean);
                System.out.println("bean<"+fileName+">生成!");
            }

        }
    }

    public static void main(String[] args) {
        new BeanFactoryImpl().init();
    }
}
