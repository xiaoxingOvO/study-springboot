package com.xx.service;

import com.xx.entity.User;

/**
 * @author xiaoxing
 * @create 2021-12-11 17:52
 */
public interface UserService {

    /**
     * 用户注册
     * @param user
     */
    void register(User user);

    /**
     * 用户登录
     * @param user
     */
    User login(User user);

}
