package com.xx;

import com.xx.dao.UserDao;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootRestfulExceptionApplicationTests {

    private StringEncryptor stringEncryptor;

    @Autowired
    public SpringbootRestfulExceptionApplicationTests(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }

    private UserDao userDao;

    @Autowired
    public SpringbootRestfulExceptionApplicationTests(UserDao userDao) {
        this.userDao = userDao;
    }


    @Test
    void contextLoads() {
    }

    @Test
    public void testSecret() {

        String secret = stringEncryptor.encrypt("root");
        System.out.println("root=" + secret);

        String localhost = stringEncryptor.encrypt("localhost");
        System.out.println("localhost=" + localhost);

        // String decrypt = stringEncryptor.decrypt("xxxxaaaabbbb==");
        // System.out.println(decrypt);
    }


    @Test
    public void testFindAll() {
        userDao.findAll().forEach(user -> System.out.println(user));
    }

}
