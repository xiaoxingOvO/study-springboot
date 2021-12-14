package com.xx.service.impl;

import com.xx.entity.User;
import com.xx.mapper.UserMapper;
import com.xx.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

/**
 * @author xiaoxing
 * @create 2021-12-11 17:52
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 注册
     * @param user
     */
    @Override
    public void register(User user) {
        //1.查询用户是否存在,已存在则报错
        User userName = userMapper.selectByUserName(user.getUsername());
        if (!ObjectUtils.isEmpty(userName)) throw new RuntimeException("用户名已存在!");
        //2.不存在则注册
        //进行密码加密
        String newPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(newPassword);
        log.debug("加密后的密码:{}",newPassword);
        userMapper.register(user);
    }

    /**
     * 用户登录
     * @param user
     */
    @Override
    public User login(User user) {
        //1.根据用户名查询用户是否存在
        User userDB = userMapper.selectByUserName(user.getUsername());
        if (ObjectUtils.isEmpty(userDB)) throw new RuntimeException("用户名输入错误!");
        //2.判断密码是否正常,先加密再比较
        String newPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        if(!userDB.getPassword().equals(newPassword)) throw new RuntimeException("密码错误!");
        return userDB;
    }
}
