package com.xx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//修饰范围：用在类上 只能用在类上 只能使用一次
//作用：标志这个类是一个spring boot入口类 启动整个spring boot项目总入口
//搭建一个springboot项目环境(可以直接创建一个springboot项目,以下内容自动加载):
//      1.pom文件引入依赖
//      2.resources生产applic.xml
//      3.创建入口类加入@SpringBootApplication注解,在main中启动项目
@SpringBootApplication
/*
 * @SpringBootApplication:注解
 *       组合注解:就是由多个注解组合而成一个注解
 *       元注解:用来修饰注解的注解 @target指定注解范围   @Retention:指定注解什么时候有效
 * @SpringBootConfiguration:这个注解就是用来自动配置spring springmvc(初始化 servlt)相关环境
 * @EnableAutoConfiguration:开启自动配置 自动配置核心注解 自动配置spring相关环境 自动与项目中引入第三方技术自动配置环境
 * @ComponentSca:组件扫描 根据注解发挥注解的作用  默认扫描当前包及其子包
 *
 * */
@MapperScan("com.xx.mapper")
public class SpringbootDay2Application {

    /**
     * 启动spring应用项目main函数
     * 参数一：指定入口类的类对象.class（指的是这个类.class，不是new的类的对象）
     * @param args main函数的参数
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringbootDay2Application.class, args);
    }

}
