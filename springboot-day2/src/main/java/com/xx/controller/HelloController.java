package com.xx.controller;

import com.xx.pojo.User;
import com.xx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiaoxing
 * @create 2021-12-09 18:54
 */

@RestController
@RequestMapping("test")
public class HelloController {

    private UserService userService;

    @Autowired
    public HelloController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/hello")
    public String hello(){
        System.out.println("hello springboot!");
        return "hello springboot!";
    }


    @RequestMapping("/findall")
    public List<User> findAll(){
        return userService.findAll();
    }
}
