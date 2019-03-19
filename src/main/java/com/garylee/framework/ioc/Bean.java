package com.garylee.framework.ioc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GaryLee on 2019-03-18 22:32.
 */
public class Bean {
    private String name;//唯一标识
    private String className;//类名
    private List<Property> properties = new ArrayList<>();

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
