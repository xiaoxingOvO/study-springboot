package com.xx;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xx.system.entity.User;
import com.xx.system.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    //查询全部用户
    @Test
    void testSelectList() {

        //查询是一个Warpper,条件构造器,这里先不用
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    //插入
    @Test
    public void testInsert() {
        User user = new User();
        user.setName("xx");
        user.setAge(18);
        user.setEmail("793408755@qq.com");
        int result = userMapper.insert(user);
        System.out.println(result);
        System.out.println(user);
    }

    //更新
    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(5L);
        user.setName("MyBatisPlus");
        userMapper.updateById(user);
    }

    //测试乐观锁
    @Test
    public void testOptimisticLocker() {
        //1.查询用户信息
        User user = userMapper.selectById(1);
        //2.修改用户信息
        user.setName("xx");
        user.setAge(20);
        //3.执行更新操作
        userMapper.updateById(user);

    }

    //测试查询
    @Test
    public void testSelect() {
        User user = userMapper.selectById(8L);
        System.out.println("user = " + user);
    }


    //测试批量查询
    @Test
    public void testSelectByBatchId() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    //测试条件查询 map
    @Test
    public void testSelectByBatchIds() {
        HashMap<String, Object> map = new HashMap<>();
        //自定义查询条件
        // map.put("name", "xx");
        map.put("age", 18);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    //测试分页查询
    @Test
    public void testPage() {
        Page<User> page = new Page<>(1, 5);
        long total = page.getTotal();
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());
        System.out.println(page.getPages());
    }

    //测试删除
    @Test
    public void testDeleteById() {
        userMapper.deleteById(9L);
    }

    //测试批量删除
    @Test
    public void testDeleteBatchId() {
        userMapper.deleteBatchIds(Arrays.asList(2, 3, 4));
    }

    //测试条件删除
    @Test
    public void testDeleteMap() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "xx1");
        userMapper.deleteByMap(map);
    }

}
