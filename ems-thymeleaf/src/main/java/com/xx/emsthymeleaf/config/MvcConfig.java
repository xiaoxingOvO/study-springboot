package com.xx.emsthymeleaf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xiaoxing
 * @create 2021-12-04 13:05
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    //通过这里的配置：不需要为每一个thymeleaf模板页面单独写个controller请求了
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //ViewController:请求路径  ViewName：跳转视图
        registry.addViewController("login").setViewName("login");
        registry.addViewController("regist").setViewName("regist");
        registry.addViewController("add").setViewName("add");
        registry.addViewController("addEmp").setViewName("addEmp");
    }
}
