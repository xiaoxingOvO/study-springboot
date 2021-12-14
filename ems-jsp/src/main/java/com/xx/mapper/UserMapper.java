package com.xx.mapper;

import com.xx.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 用户注册
     * @param user
     */
    void register(User user);

    int insertSelective(User record);

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    User selectByUserName(String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}