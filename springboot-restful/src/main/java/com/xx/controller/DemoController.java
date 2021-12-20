package com.xx.controller;

import com.xx.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiaoxing
 * @create 2021-12-14 21:55
 */

@Controller
@RequestMapping("demo")
public class DemoController {

    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    //查询一个      传统wbe开发: 不是restful风格  就是用 @RequestMapping映射请求路径:  支持任何一个请求请求这个路径
    @RequestMapping("findUserById")     //GET POST DELETE PUT PATCH
    public String findUserById(Integer id) {
        log.debug("查询的用户id为:{}", id);
        User user = new User(1, "小红", 2345.34, new Date());
        //存储作用域
        // model.addAttribute(user);
        return "showOne";
    }

    //查询所有
    @RequestMapping("findAll")
    public String fingAll() {
        log.debug("查询所有用户");
        List<User> userList = new ArrayList<User>();
        userList.add(new User(1, "小红", 2345.34, new Date()));
        // model.addAllAttributes()
        return "showAll";
    }

    //保存
    @RequestMapping("save")
    public String save(User user) {
        log.debug("user:{}", user);
        return "redirect:/user/findAll";
    }

    //修改
    public String update(User user) {
        log.debug("user:{}", user);
        return "redirect:/user/findAll";
    }

    //删除
    @RequestMapping("delete")
    public String delete(Integer id) {
        log.debug("删除用户id为:{}", id);
        return "redirect:/user/findAll";
    }
}
