package com.xx.service;

import com.xx.mapper.UserMapper;
import com.xx.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaoxing
 * @create 2021-12-10 13:27
 */
@Service
public class UserServiceImpl implements UserService {

    //声明一个日志对象
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> findAll() {
        log.debug("日志测试----------------");
        return userMapper.findAll();
    }
}
