package com.garylee.framework.structure;

import com.garylee.framework.utils.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GaryLee on 2018-10-25 22:31.
 */
public class HtmlFactory {
    public static Map<String,String> htmlMap;
    public static Map<String,String> getHtml(){
        //Tomcat工作缓存区，相当于安装板的root目录所在地，在这里放在工程目录下。
        File file = new File(Config.targetPath+"\\templates");
        htmlMap = new HashMap<>();
        initMap(file);
        return htmlMap;
    }
    public static void initMap(File file){
        File[] files = file.listFiles();
        for(File f:files){
            if(f.isDirectory()){
                initMap(f);
            }else if(f.getName().endsWith(".html")){
                String key = f.getName().replace(".html","");//key
                StringBuffer value = new StringBuffer();//value
                String str = null;
                try(BufferedReader bufferedReader = new BufferedReader(new FileReader(f))){
                    while((str = bufferedReader.readLine())!=null) {
                        value.append(System.lineSeparator()+str);//输出后html的第一行会是空行
                    }
                }catch (Exception e){

                }
                htmlMap.put(key,value.toString());
//                System.out.println(value.toString());
            }
        }
    }
}
