package com.zz4955.annotation_autowired_ex.service;

/**
 * Spring注解的原理与自定义注解的实现：https://blog.csdn.net/Wooyulin/article/details/79158483
 * 以@Resource为例，透析注解的本质，spring中是如何使用注解的：https://blog.csdn.net/qq_27093465/article/details/52702463
 */
public class ClassPathXMLApplicationContextTest {

    public static void main(String[] args) {
        ClassPathXMLApplicationContext path = new ClassPathXMLApplicationContext("configAnnotation.xml");
        UserServiceImpl userService = (UserServiceImpl)path.getBean("userService");
        userService.show();
    }
}

