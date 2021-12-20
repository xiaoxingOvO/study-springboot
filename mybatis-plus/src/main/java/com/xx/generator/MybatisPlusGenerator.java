package com.xx.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 代码生成器
 *
 * @author xiaoxing
 * @create 2021-12-20 10:45
 */
public class MybatisPlusGenerator {
    public static void main(String[] args) {

        //数据库表
        List<String> tables = new ArrayList<>();
        tables.add("user");


        FastAutoGenerator.create("jdbc:mysql://localhost:3306/mybatis_plus?serverTimezone=GMT", "root", "root")
                .globalConfig(builder -> {
                    builder.author("xiaoxing") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .commentDate("yyyy-MM-dd")  //时间
                            // 指定输出目录System.getProperty("user.dir")输出的是项目名springboot
                            .outputDir(System.getProperty("user.dir") + "/mybatis-plus/src/main/java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.xx") // 设置父包名
                            .moduleName("system") // 设置父包模块名
                            .entity("entity")
                            .service("service")
                            //.serviceImpl("serviceImpl")
                            .controller("controller")
                            .mapper("mapper")
                            .xml("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir") + "/mybatis-plus/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名
                            .addTablePrefix("t_", "c_")// 设置过滤表前缀
                            .serviceBuilder()   //service策略配置
                            .formatServiceFileName("%sService") //service类名,%s适配,根据表名替换
                            .formatServiceImplFileName("%sServiceImpl") //同上
                            .entityBuilder()    //实体类策略配置
                            .naming(NamingStrategy.underline_to_camel)  //数据库表映射到实体的命名策略，下划线转驼峰命名
                            .idType(IdType.AUTO)    //设置注解策略类型
                            .enableLombok() //开启lombok
                            .logicDeleteColumnName("deleted")   //说明逻辑删除是哪个字段
                            .versionColumnName("version")   //说明乐观锁是哪个字段
                            .enableTableFieldAnnotation()   //属性加上说明注解
                            .controllerBuilder()    //controller策略配置
                            .formatFileName("%sController") //controller类名
                            .enableRestStyle()  //开始RestController
                            .mapperBuilder()    //mapper策略配置
                            .superClass(BaseMapper.class)   //继承哪个父类
                            .formatMapperFileName("%sMapper")   //mapper接口名
                            .enableMapperAnnotation()   //@mapper开启
                            .formatXmlFileName("%sMapper"); //xml名

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
