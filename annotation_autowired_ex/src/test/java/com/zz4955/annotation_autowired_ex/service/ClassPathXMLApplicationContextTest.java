package com.zz4955.annotation_autowired_ex.service;

public class ClassPathXMLApplicationContextTest {

    public static void main(String[] args) {
        ClassPathXMLApplicationContext path = new ClassPathXMLApplicationContext("configAnnotation.xml");
        UserServiceImpl userService = (UserServiceImpl)path.getBean("userService");
        userService.show();
    }
}

