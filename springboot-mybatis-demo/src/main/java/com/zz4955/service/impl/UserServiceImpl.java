package com.zz4955.service.impl;

import com.github.pagehelper.PageHelper;
import com.zz4955.mapper.UserMapper;
import com.zz4955.model.User;
import com.zz4955.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.selectAllUser();
    }
}
