package com.xx;

import com.xx.service.DemoService;
import com.xx.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootDay2ApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private DemoService demoService;

    @Test
    void contextLoads() {
        userService.findAll().forEach(user -> System.out.println(user));
    }

    @Test
    public void test() {
        demoService.demo();
    }

}
