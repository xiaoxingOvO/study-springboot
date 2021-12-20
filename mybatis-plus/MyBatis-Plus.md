## MyBatis-Plus

### 简介

[MyBatis-Plus](https://github.com/baomidou/mybatis-plus)（简称 MP）是一个 [MyBatis](https://www.mybatis.org/mybatis-3/)的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。

### 特性

- **无侵入**：只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑
- **损耗小**：启动即会自动注入基本 CURD，性能基本无损耗，直接面向对象操作
- **强大的 CRUD 操作**：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
- **支持 Lambda 形式调用**：通过 Lambda 表达式，方便的编写各类查询条件，无需再担心字段写错
- **支持主键自动生成**：支持多达 4 种主键策略（内含分布式唯一 ID 生成器 - Sequence），可自由配置，完美解决主键问题
- **支持 ActiveRecord 模式**：支持 ActiveRecord 形式调用，实体类只需继承 Model 类即可进行强大的 CRUD 操作
- **支持自定义全局通用操作**：支持全局通用方法注入（ Write once, use anywhere ）
- **内置代码生成器**：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，更有超多自定义配置等您来使用
- **内置分页插件**：基于 MyBatis 物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同于普通 List 查询
- **分页插件支持多种数据库**：支持 MySQL、MariaDB、Oracle、DB2、H2、HSQL、SQLite、Postgre、SQLServer 等多种数据库
- **内置性能分析插件**：可输出 SQL 语句以及其执行时间，建议开发测试时启用该功能，能快速揪出慢查询
- **内置全局拦截插件**：提供全表 delete 、 update 操作智能分析阻断，也可自定义拦截规则，预防误操作

## 快速入门

[官方文档](https://baomidou.com/)

### 创建数据库

```sql
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id BIGINT(20) NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');
```



### 初始化工程

> 可以使用 [Spring Initializer](https://start.spring.io/)快速初始化一个 Spring Boot 工程

### 添加依赖

```xml
        <!--mybatis-plus-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.3.4</version>
        </dependency>

        <!--使用durid连接池的依赖-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.2.8</version>
        </dependency>

        <!--mysql依赖-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.16</version>
        </dependency>

        <!--lombok用来简化实体类-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
```

### 配置文件

在 `application.yml` 配置文件中添加 H2 数据库数据库的相关配置：

```yml
server:
  port: 8080
  servlet:
    context-path: /mybatis-plus

#DataSourece Config
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/mybatis_plus?useSSL=true&useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
    username: root
    password: root
#log config,配置之后控制台可以看到sql
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

在 Spring Boot 启动类中添加 `@MapperScan` 注解，扫描 Mapper 文件夹：

```java
@SpringBootApplication
@MapperScan("com.xx.mapper")
public class MybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class, args);
    }

}
```

#### 编码

编写实体类`User.java`

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private Integer age;
    private String email;

}
```

编写mapper类接口继承BaseMapper

```java
public interface UserMapper extends BaseMapper<User> {
    //所有的CRUD
}
```

#### 开始使用

添加测试类,进行测试:

```java
@SpringBootTest
class MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testSelect() {
        //查询是一个Warpper,条件构造器,这里先不用
        //查询全部用户
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

}
```

> UserMapper 中的 `selectList()` 方法的参数为 MP 内置的条件封装器 `Wrapper`，所以不填写就是无任何条件

控制台输出

```java
User(id=1, name=Jone, age=18, email=test1@baomidou.com)
User(id=2, name=Jack, age=20, email=test2@baomidou.com)
User(id=3, name=Tom, age=28, email=test3@baomidou.com)
User(id=4, name=Sandy, age=21, email=test4@baomidou.com)
User(id=5, name=Billie, age=24, email=test5@baomidou.com)
```

通过以上几个简单的步骤，我们就实现了 User 表的 CRUD 功能，甚至连 XML 文件都不用编写！

### 注解

#### @TableName

- 描述：表名注解，标识实体类对应的表
- 使用位置：实体类

```java {1}
@TableName("sys_user")
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

| 属性             | 类型     | 必须指定 | 默认值 | 描述                                                         |
| :--------------- | :------- | :------- | :----- | :----------------------------------------------------------- |
| value            | String   | 否       | ""     | 表名                                                         |
| schema           | String   | 否       | ""     | schema                                                       |
| keepGlobalPrefix | boolean  | 否       | false  | 是否保持使用全局的 tablePrefix 的值（当全局 tablePrefix 生效时） |
| resultMap        | String   | 否       | ""     | xml 中 resultMap 的 id                                       |
| autoResultMap    | boolean  | 否       | false  | 是否自动构建 resultMap 并使用（如果设置 resultMap 则不会进行 resultMap 的自动构建并注入） |
| excludeProperty  | String[] | 否       | {}     | 需要排除的属性名 <Badge text="@since 3.3.1" type="tip" vertical="middle"/> |

> warning 关于 `autoResultMap` 的说明：
>
> MP 会自动构建一个 `resultMap` 并注入到 MyBatis 里（一般用不上），请注意以下内容：
>
> 因为 MP 底层是 MyBatis，所以 MP 只是帮您注入了常用 CRUD 到 MyBatis 里，注入之前是动态的（根据您的 Entity 字段以及注解变化而变化），但是注入之后是静态的（等于 XML 配置中的内容）。
>
> ---
>
> 而对于 `typeHandler` 属性，MyBatis 只支持写在 2 个地方:
>
> 1. 定义在 resultMap 里，作用于查询结果的封装
> 2. 定义在 `insert` 和 `update` 语句的 `#{property}` 中的 `property` 后面（例：`#{property,typehandler=xxx.xxx.xxx}`），并且只作用于当前 `设置值`
>
> 除了以上两种直接指定 `typeHandler` 的形式，MyBatis 有一个全局扫描自定义 `typeHandler` 包的配置，原理是根据您的 `property` 类型去找其对应的 `typeHandler` 并使用。
>

#### @TableId

- 描述：主键注解
- 使用位置：实体类主键字段

```java {3}
@TableName("sys_user")
public class User {
    @TableId
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

| 属性  | 类型   | 必须指定 | 默认值      | 描述         |
| :---- | :----- | :------- | :---------- | :----------- |
| value | String | 否       | ""          | 主键字段名   |
| type  | Enum   | 否       | IdType.NONE | 指定主键类型 |

##### IdType

| 值                | 描述                                                         |
| :---------------- | :----------------------------------------------------------- |
| AUTO              | 数据库 ID 自增                                               |
| NONE              | 无状态，该类型为未设置主键类型（注解里等于跟随全局，全局里约等于 INPUT） |
| INPUT             | insert 前自行 set 主键值                                     |
| ASSIGN_ID         | 分配 ID(主键类型为 Number(Long 和 Integer)或 String)(since 3.3.0),使用接口`IdentifierGenerator`的方法`nextId`(默认实现类为`DefaultIdentifierGenerator`雪花算法) |
| ASSIGN_UUID       | 分配 UUID,主键类型为 String(since 3.3.0),使用接口`IdentifierGenerator`的方法`nextUUID`(默认 default 方法) |
| ~~ID_WORKER~~     | 分布式全局唯一 ID 长整型类型(please use `ASSIGN_ID`)         |
| ~~UUID~~          | 32 位 UUID 字符串(please use `ASSIGN_UUID`)                  |
| ~~ID_WORKER_STR~~ | 分布式全局唯一 ID 字符串类型(please use `ASSIGN_ID`)         |

#### @TableField

- 描述：字段注解（非主键）

| 属性             | 类型                         | 必须指定 | 默认值                   | 描述                                                         |
| :--------------- | :--------------------------- | :------- | :----------------------- | :----------------------------------------------------------- |
| value            | String                       | 否       | ""                       | 数据库字段名                                                 |
| el               | String                       | 否       | ""                       | 映射为原生 `#{ ... }` 逻辑,相当于写在 xml 里的 `#{ ... }` 部分 |
| exist            | boolean                      | 否       | true                     | 是否为数据库表字段                                           |
| condition        | String                       | 否       | ""                       | 字段 `where` 实体查询比较条件，有值设置则按设置的值为准，没有则为默认全局的 `%s=#{%s}`，[参考](https://github.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-annotation/src/main/java/com/baomidou/mybatisplus/annotation/SqlCondition.java) |
| update           | String                       | 否       | ""                       | 字段 `update set` 部分注入，例如：`update="%s+1"` 表示更新时会 `set version=version+1` （该属性优先级高于 `el` 属性） |
| insertStrategy   | Enum                         | 否       | FieldStrategy.DEFAULT    | 举例：NOT_NULL <br/> `insert into table_a(<if test="columnProperty != null">column</if>) values (<if test="columnProperty != null">#{columnProperty}</if>)` |
| updateStrategy   | Enum                         | 否       | FieldStrategy.DEFAULT    | 举例：IGNORED <br/> `update table_a set column=#{columnProperty}` |
| whereStrategy    | Enum                         | 否       | FieldStrategy.DEFAULT    | 举例：NOT_EMPTY <br/> `where <if test="columnProperty != null and columnProperty!=''">column=#{columnProperty}</if>` |
| fill             | Enum                         | 否       | FieldFill.DEFAULT        | 字段自动填充策略                                             |
| select           | boolean                      | 否       | true                     | 是否进行 select 查询                                         |
| keepGlobalFormat | boolean                      | 否       | false                    | 是否保持使用全局的 format 进行处理                           |
| jdbcType         | JdbcType                     | 否       | JdbcType.UNDEFINED       | JDBC 类型 (该默认值不代表会按照该值生效)                     |
| typeHandler      | Class<? extends TypeHandler> | 否       | UnknownTypeHandler.class | 类型处理器 (该默认值不代表会按照该值生效)                    |
| numericScale     | String                       | 否       | ""                       | 指定小数点后保留的位数                                       |

>  关于`jdbcType`和`typeHandler`以及`numericScale`的说明:
> `numericScale`只生效于 update 的 sql.
> `jdbcType`和`typeHandler`如果不配合`@TableName#autoResultMap = true`一起使用,也只生效于 update 的 sql.
> 对于`typeHandler`如果你的字段类型和 set 进去的类型为`equals`关系,则只需要让你的`typeHandler`让 Mybatis 加载到即可,不需要使用注解

##### FieldStrategy

| 值        | 描述                                                        |
| :-------- | :---------------------------------------------------------- |
| IGNORED   | 忽略判断                                                    |
| NOT_NULL  | 非 NULL 判断                                                |
| NOT_EMPTY | 非空判断(只对字符串类型字段,其他类型字段依然为非 NULL 判断) |
| DEFAULT   | 追随全局配置                                                |

##### FieldFill

| 值            | 描述                 |
| :------------ | :------------------- |
| DEFAULT       | 默认不处理           |
| INSERT        | 插入时填充字段       |
| UPDATE        | 更新时填充字段       |
| INSERT_UPDATE | 插入和更新时填充字段 |

#### @Version

- 描述：乐观锁注解、标记 `@Verison` 在字段上

#### @EnumValue

- 描述：通枚举类注解(注解在枚举字段上)

#### @TableLogic

- 描述：表字段逻辑处理注解（逻辑删除）

| 属性   | 类型   | 必须指定 | 默认值 | 描述         |
| :----- | :----- | :------- | :----- | :----------- |
| value  | String | 否       | ""     | 逻辑未删除值 |
| delval | String | 否       | ""     | 逻辑删除值   |

#### @SqlParser<Badge text="Deprecated" type="warn"/>

> see [@InterceptorIgnore](#InterceptorIgnore)

#### @KeySequence

- 描述：序列主键策略 `oracle`
- 属性：value、resultMap

| 属性  | 类型   | 必须指定 | 默认值     | 描述                                                         |
| :---- | :----- | :------- | :--------- | :----------------------------------------------------------- |
| value | String | 否       | ""         | 序列名                                                       |
| clazz | Class  | 否       | Long.class | id 的类型, 可以指定 String.class，这样返回的 Sequence 值是字符串"1" |

#### @InterceptorIgnore

> see [插件主体](/pages/2976a3/)

#### @OrderBy

- 描述：内置 SQL 默认指定排序，优先级低于 wrapper 条件查询

| 属性   | 类型    | 必须指定 | 默认值          | 描述           |
| :----- | :------ | :------- | :-------------- | :------------- |
| isDesc | boolean | 否       | true            | 是否倒序查询   |
| sort   | short   | 否       | Short.MAX_VALUE | 数字越小越靠前 |

## CRUD扩展

### Insert

```java
    //插入
    @Test
    public void testInsert() {
        User user = new User();
        user.setName("xx");
        user.setAge(3);
        user.setEmail("793408755@qq.com");
        int result = userMapper.insert(user);
        System.out.println(result);
        System.out.println(user);
    }
```

控制台输出

```java
==>  Preparing: INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
==> Parameters: 1472421837744594946(Long), xx(String), 3(Integer), 793408755@qq.com(String)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@705a8dbc]
1
User(id=1472421837744594946, name=xx, age=3, email=793408755@qq.com)
```

> 数据库插入的id默认为:全局的唯一id

### 主键生成策略

分布式系统唯一Id生成：https://www.cnblogs.com/haoxinyue/p/5208136.html

**Twitter的snowflake算法**(雪花算法)

snowflake是Twitter开源的分布式ID生成算法，结果是一个long型的ID。其核心思想是：使用41bit作为毫秒数，10bit作为机器的ID（5个bit是数据中心（北京、香港···），5个bit的机器ID），12bit作为毫秒内的流水号（意味着每个节点在每毫秒可以产生 4096 个 ID），最后还有一个符号位，永远是0。

> **主键自增**

1.实体类主键字段上添加注解,IdType为AUTO

2.数据库中ID字段要设置为自增

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;

}
```

![image-20211219124848042](https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112191248083.png)

**@TableId**	描述:主键注解		使用位置:实体类主键字段

| 属性  | 类型   | 必须指定 | 默认值      | 描述         |
| ----- | ------ | -------- | ----------- | ------------ |
| value | String | 否       | ""          | 主键字段名   |
| type  | Enum   | 否       | IdType.NONE | 指定主键类型 |

**IdType**

| 值          | 描述                                                         |
| ----------- | ------------------------------------------------------------ |
| AUTO        | 数据库 ID 自增                                               |
| NONE        | 无状态，该类型为未设置主键类型（注解里等于跟随全局，全局里约等于 INPUT） |
| INPUT       | insert 前自行 set 主键值                                     |
| ASSIGN_ID   | 分配 ID(主键类型为 Number(Long 和 Integer)或 String)(since 3.3.0),使用接口`IdentifierGenerator`的方法`nextId`(默认实现类为`DefaultIdentifierGenerator`雪花算法) |
| ASSIGN_UUID | 分配 UUID,主键类型为 String(since 3.3.0),使用接口`IdentifierGenerator`的方法`nextUUID`(默认 default 方法) |

### update

测试

```java
//更新
    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(5L);
        user.setName("学习MyBatisPlus");
        //注意:参数是对象
        userMapper.updateById(user);
    }
```

控制太输出

```java
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@73c3cd09] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@5f1483fd] will not be managed by Spring
==>  Preparing: UPDATE user SET name=? WHERE id=?
==> Parameters: 学习MyBatisPlus(String), 5(Long)
<==    Updates: 1
```

> 发现:通过条件自动拼接动态sql

### 自动填充

创建时间create_time、修改时间update_time!一般是自动化完成，而不是我们手动更新(插入数据时手动set写入)

阿里巴巴开发手册:所有的数据库表:gmt_create、gmt_modified几乎所有的表都要配置上!并且需要自动化!

> 方式一:数据库级别（工作中一般不需要修改数据库，所有不怎么使用）

1.在数据库表中增加字段create_time、update_time

2.测试插入方法，实体类要添加上这两个字段

![image-20211219131124537](https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112191311600.png)

```java
private Date createTime;
private Date updateTime;
```
```java
//更新
@Test
public void testUpdate() {
    User user = new User();
    user.setId(5L);
    user.setName("MyBatisPlus");
    userMapper.updateById(user);
}
```

![image-20211219132329587](https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112191323630.png)

> 方式二;代码级别

1.数据库添加字段

2.实体类字段属性上添加注解

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
3.编写自定义实现类 MyMetaObjectHandler

1. 实现MetaObjectHandler
2. 重写inserFill和updateFill方法
3. 调用setFieldValByName方法

```java
@Slf4j
@Component
public class MyMetaObjectHander implements MetaObjectHandler {

    //插入时的填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill.....");
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }

    //更新时填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }
}
```

### 乐观锁

> 乐观锁:总是认为不会出现问题,无论干什么都不去上锁!如果出现问题,再次更新值测试
>
> 悲观锁:总是认为总是会出现问题,无论干什么都会上锁!

乐观锁实现方式：

- 取出记录时，获取当前 version
- 更新时，带上这个 version
- 执行更新时， set version = newVersion where version = oldVersion
- 如果 version 不对，就更新失败

> 测试乐观锁插件

1.数据库表增加version字段

```java
@Version    //乐观锁注解
private Integer version;
```

2.配置插件

```java
@Configuration
@MapperScan("com.xx.mapper")
public class MyBatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return mybatisPlusInterceptor;
    }

}
```

3.测试

```java
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
```

控制台输出

![image-20211219154929471](https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112191549550.png)

### select

```java
//测试查询
@Test
public void testSelect() {
    User user = userMapper.selectById(1L);
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
```

### 分页查询

1.limit分页

2.pageHelper等三方插件

3.mybatis-plus内置分页插件

> 分页查询

1.配置插件

```java

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }
```

2.测试

```java
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
```

### delete

```java
//测试删除
@Test
public void testDeleteById() {
    userMapper.deleteById(5L);
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
```

### 逻辑删除

> 物理删除;从数据中直接移除
>
> 逻辑删除:在数据库中没有被移除

管理员可以查看被删除的记录,防止数据丢失,类似于回收站

> 测试逻辑删除

1.数据库表添加字段

2.修改实体类(3.3.0后可以不用添上注解)

```java
@TableLogic //逻辑删除注解
private Integer deleted;
```

3.配置yml

```yml
mybatis-plus:
  global-config:
    db-config:
      logic-delete-field: flag # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
      logic-delete-value: 1 # 逻辑已删除值(默认为 1)
      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)

```

### 执行SQL分析打印

平时开发时会遇到一些慢sql

作用:用于输出每条SQL语句及其执行时间

1.引入p6spy依赖

```java
<dependency>
  <groupId>p6spy</groupId>
  <artifactId>p6spy</artifactId>
  <version>最新版本</version>
</dependency>
```

2.application.yml配置

```yaml
spring:
  datasource:
    driver-class-name: com.p6spy.engine.spy.P6SpyDriver
    url: jdbc:p6spy:h2:mem:test
    ...
```

3.spy.properties配置

```properties
#3.2.1以上使用
modulelist=com.baomidou.mybatisplus.extension.p6spy.MybatisPlusLogFactory,com.p6spy.engine.outage.P6OutageFactory
#3.2.1以下使用或者不配置
#modulelist=com.p6spy.engine.logging.P6LogFactory,com.p6spy.engine.outage.P6OutageFactory
# 自定义日志打印
logMessageFormat=com.baomidou.mybatisplus.extension.p6spy.P6SpyLogger
#日志输出到控制台
appender=com.baomidou.mybatisplus.extension.p6spy.StdoutLogger
# 使用日志系统记录 sql
#appender=com.p6spy.engine.spy.appender.Slf4JLogger
# 设置 p6spy driver 代理
deregisterdrivers=true
# 取消JDBC URL前缀
useprefix=true
# 配置记录 Log 例外,可去掉的结果集有error,info,batch,debug,statement,commit,rollback,result,resultset.
excludecategories=info,debug,result,commit,resultset
# 日期格式
dateformat=yyyy-MM-dd HH:mm:ss
# 实际驱动可多个
#driverlist=org.h2.Driver
driverlist=com.mysql.cj.jdbc.Driver
# 是否开启慢SQL记录
outagedetection=true
# 慢SQL记录标准 2 秒
outagedetectioninterval=20
```

### 条件构造器

```
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
```



### 代码自动生成器

```java
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
                            .serviceImpl("serviceImpl")
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
```

如果实体类APIModel报红

```yaml
<!--配置ApiModel在实体类中不生效-->
<dependency>
  <groupId>com.spring4all</groupId>
  <artifactId>spring-boot-starter-swagger</artifactId>
  <version>1.5.1.RELEASE</version>
</dependency>
```

