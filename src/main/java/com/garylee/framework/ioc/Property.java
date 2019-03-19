package com.garylee.framework.ioc;

/**
 * Created by GaryLee on 2019-03-18 22:57.
 * 参数列表
 */
public class Property {
    private String name;//参数名
    private String value;//参数

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
