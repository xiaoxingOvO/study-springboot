package com.xx.emsthymeleaf.service.impl;

import com.xx.emsthymeleaf.mapper.UserMapper;
import com.xx.emsthymeleaf.pojo.User;
import com.xx.emsthymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author xiaoxing
 * @create 2021-12-06 14:12
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    //用户登录
    @Override
    public User login(String username, String password) {
        //1.根据用户名查询用户名
        User user = userMapper.findByUserName(username);
        if (ObjectUtils.isEmpty(user)) throw new RuntimeException("用户名错误或用户不存在!");
        //2.比较密码,注意先加密再比较
        String ps = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (!user.getPassword().equals(ps)) throw new RuntimeException("密码错误!");
        return user;
    }

    //用户注册
    @Override
    public void register(User user) {
        //1.根据用户名查询数据中是否存在该用户
        User userName = userMapper.findByUserName(user.getUsername());
        //2.判断用户是否存在
        if (!ObjectUtils.isEmpty(userName)) throw new RuntimeException("当前用户名已被注册!");
        //3.注册用户&m密码加密
        String newPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(newPassword);
        userMapper.save(user);

    }
}
