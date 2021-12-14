package com.xx.smsthymeleaf;

import com.xx.smsthymeleaf.mapper.StudentMapper;
import com.xx.smsthymeleaf.pojo.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Timestamp;

@SpringBootTest
class SmsThymeleafApplicationTests {

    @Resource
    private StudentMapper studentMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void insert(){
        Student student= new Student();
        student.setStuName("xx");
        student.setStuNum("1001");
        student.setStuPwd("123456");
        student.setStuGender((byte) 1);
        student.setStuMajor((byte) 1);
        student.setStuTime("2020-10-10");
        student.setStuRemark("123123");
        studentMapper.insert(student);

    }

}
