package com.garylee.framework.handler;

import com.garylee.framework.servlet.DispatcherServlet;
import com.garylee.framework.structure.MethodMap;
import com.garylee.framework.structure.StaticFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
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
            if (!methodMap.isBody()) {
                String fileName = methodMap.getMethod().invoke(object).toString();
                if (htmlMap.containsKey(fileName)){
                    resp.setContentType("text/html; charset=UTF-8");//乱码加上这句
                    resp.getWriter().write(htmlMap.get(fileName));
                }
            }else if (methodMap.isBody()){
                //获取反射方法的参数类型(方便转化)
                Class<?>[] classz = methodMap.getMethod().getParameterTypes();
                //获取反射方法的形参名(方便获取value的值--map实现)
                //此处需1.8编译版本,与javac -parameters,否则返回值均为arg0,arg1...
                Parameter[] parameters = methodMap.getMethod().getParameters();
                //存放参数值,按顺序
                //如int a,int b对应的是2,3
                List<Object> objects = new ArrayList<>();
                for(int i=0;i<classz.length;i++) {
                    fillList(objects, classz[i], map.get(parameters[i].getName())[0]);//默认只有一个参数
                }
                //调用,返回o是结果(return)
                //invoke参数为class对象和参数值数组
                Object o = methodMap.getMethod().invoke(object,objects.toArray());
//                System.out.println("返回的object:"+o);
                resp.getWriter().print(o);
            }
        }catch (Exception e){
            e.printStackTrace();
            //如果参数传少了就返回错误信息
            try {
                resp.setContentType("text/html; charset=UTF-8");//乱码加上这句
                resp.getWriter().print("<h1>参数缺失!</h1>");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    //将map获取到的值转化为方法参数的类型,并按顺序存放到list
    private static void fillList(List<Object> list, Class<?> parameter,Object value) {
//        System.out.println(parameter.getName());
        if("java.lang.String".equals(parameter.getName())){
            list.add(value);
        }else if("java.lang.Character".equals(parameter.getName())){
            char[] ch = ((String)value).toCharArray();
            list.add(ch[0]);
        }else if("char".equals(parameter.getName())){
            char[] ch = ((String)value).toCharArray();
            list.add(ch[0]);
        }else if("java.lang.Double".equals(parameter.getName())){
            list.add(Double.parseDouble((String) value));
        }else if("double".equals(parameter.getName())){
            list.add(Double.parseDouble((String) value));
        }else if("java.lang.Integer".equals(parameter.getName())){
            list.add(Integer.parseInt((String) value));
        }else if("int".equals(parameter.getName())){
            list.add(Integer.parseInt((String) value));
        }else if("java.lang.Long".equals(parameter.getName())){
            list.add(Long.parseLong((String) value));
        }else if("long".equals(parameter.getName())){
            list.add(Long.parseLong((String) value));
        }else if("java.lang.Float".equals(parameter.getName())){
            list.add(Float.parseFloat((String) value));
        }else if("float".equals(parameter.getName())){
            list.add(Float.parseFloat((String) value));
        }else if("java.lang.Short".equals(parameter.getName())){
            list.add(Short.parseShort((String) value));
        }else if("shrot".equals(parameter.getName())){
            list.add(Short.parseShort((String) value));
        }else if("java.lang.Byte".equals(parameter.getName())){
            list.add(Byte.parseByte((String) value));
        }else if("byte".equals(parameter.getName())){
            list.add(Byte.parseByte((String) value));
        }else if("java.lang.Boolean".equals(parameter.getName())){
            if("false".equals(value) || "0".equals(value)){
                list.add(false);
            }else if("true".equals(value) || "1".equals(value)){
                list.add(true);
            }
        }else if("boolean".equals(parameter.getName())){
            if("false".equals(value) || "0".equals(value)){
                list.add(false);
            }else if("true".equals(value) || "1".equals(value)){
                list.add(true);
            }
        }
    }
}
