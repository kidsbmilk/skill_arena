package com.zz4955.service;

import com.zz4955.model.User;

import java.util.List;

public interface UserService {

    int addUser(User user);

    List<User> findAllUser(int pageNum, int pageSize);
}
