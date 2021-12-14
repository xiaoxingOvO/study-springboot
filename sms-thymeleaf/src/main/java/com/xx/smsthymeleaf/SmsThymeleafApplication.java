package com.xx.smsthymeleaf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xx.smsthymeleaf.mapper")
public class SmsThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsThymeleafApplication.class, args);
    }

}
