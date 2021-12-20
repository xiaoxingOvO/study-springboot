package com.xx;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xx.system.entity.User;
import com.xx.system.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @author xiaoxing
 * @create 2021-12-19 21:32
 */
@SpringBootTest
public class WrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test1() {
        //查询name不为空的用户,并且邮箱不为空,年龄大于等于12
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name").isNotNull("email").ge("age", 14);
        userMapper.selectList(wrapper).forEach(System.out::println);
    }

    @Test
    public void test2() {
        //查询一个
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // wrapper.eq("name", "xx");
        wrapper.eq("age", 15);
        User user = userMapper.selectOne(wrapper);
        System.out.println("user = " + user);
    }

    @Test
    public void test3() {
        //查询年龄在15-18之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age", 15, 18);
        Long count = userMapper.selectCount(wrapper);
        System.out.println("count = " + count);
    }

    //模糊查询
    @Test
    public void test4() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.likeRight("email", "8").notLike("name", 1);
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    //嵌套查询/可以做联表
    @Test
    public void test5() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.inSql("id", "select id from user where id>11");

        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    @Test
    public void test6() {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //通过id进行排序
        wrapper.orderByAsc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

}
