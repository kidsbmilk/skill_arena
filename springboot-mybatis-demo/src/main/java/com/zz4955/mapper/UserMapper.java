package com.zz4955.mapper;

import com.zz4955.model.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    // 这个方法是我自己加的
    List<User> selectAllUser();
}