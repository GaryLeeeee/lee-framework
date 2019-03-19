package com.garylee.framework.ioc;

import com.garylee.framework.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GaryLee on 2019-03-19 14:05.
 */
public class TestIoc {
    public static void main(String[] args) throws ClassNotFoundException {

        Bean bean = new Bean();
        bean.setName("user");
        bean.setClassName(User.class.getName());

        List<Property> properties = new ArrayList<>();
        Property property = new Property();
        property.setName("id");
        property.setValue("2");
        Property property1 = new Property();
        property1.setName("name");
        property1.setValue("teemo");
        properties.add(property);
        properties.add(property1);

        bean.setProperties(properties);

        //注册
        BeanFactoryImpl.beanMap.put("user",bean);




        BeanFactory factory = new BeanFactoryImpl();
        User user = (User) factory.getBean("user");

        User user2 = (User) factory.getBean("user");
        user2.setName("nnn");


        User user3 = (User) factory.getBean("user");
        System.out.println("|user3:"+user3);
    }
}
