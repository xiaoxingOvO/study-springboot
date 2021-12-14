package com.xx.emsthymeleaf.service;

import com.xx.emsthymeleaf.pojo.User;

/**
 * @author xiaoxing
 * @create 2021-12-06 14:12
 */
public interface UserService {

    //注册用户
    void register(User user);

    //用户登录
    User login(String username, String password);
}
