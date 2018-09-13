package com.zz4955.annotation_autowired_ex.service;

import com.zz4955.annotation_autowired_ex.annotation.MyResource;
import com.zz4955.annotation_autowired_ex.dao.User1DaoImpl;
import com.zz4955.annotation_autowired_ex.dao.User2DaoImpl;
import com.zz4955.annotation_autowired_ex.dao.UserDaoImpl;

public class UserServiceImpl {

    public UserDaoImpl userDao;
    public User1DaoImpl user1Dao;

    // 字段上的注解，可以配置name属性
    @MyResource
    public User2DaoImpl user2Dao;

    // set方法上的注解，带有name属性
    @MyResource(name = "userDao")
    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    // set方法上的注解，没有配置name属性
    @MyResource
    public void setUser1Dao(User1DaoImpl user1Dao) {
        this.user1Dao = user1Dao;
    }

    public void show() {
        userDao.show();;
        user1Dao.show1();
        user2Dao.show2();
        System.out.println("这里是Service方法......");
    }
}
