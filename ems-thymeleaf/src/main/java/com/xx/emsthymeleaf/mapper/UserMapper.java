package com.xx.emsthymeleaf.mapper;

import com.xx.emsthymeleaf.pojo.User;

/**
 * @author xiaoxing
 * @create 2021-12-06 14:19
 */
public interface UserMapper {

    //根据用户名查询用户
    User findByUserName(String username);

    //保存用户信息
    void save(User user);
}
