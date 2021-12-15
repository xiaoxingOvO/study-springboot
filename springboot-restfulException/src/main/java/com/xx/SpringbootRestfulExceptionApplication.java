package com.xx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xx.dao")
public class SpringbootRestfulExceptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRestfulExceptionApplication.class, args);
    }

}
