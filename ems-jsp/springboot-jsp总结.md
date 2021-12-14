# springboot-jsp小项目

## 1.库表设计

该系统需要两张表:	用户表user		员工表employee

表与表之间关系:		独立的两张表

确定字段:	user:  		id 、username、password、realname、gender 

​						employee：  id 、 name、birthday、salary、gender

数据库:	ems-jsp

## 2.环境搭建

### 1.创建项目

### 2.引入依赖

```xml
<dependencies>
    <!--web-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!--test-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <!--jsp解析依赖-->
    <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-jasper</artifactId>
    </dependency>
    <!--jstl-->
    <dependency>
        <groupId>jstl</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>
    <!--开启热部署-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
    <!--druid-->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid-spring-boot-starter</artifactId>
        <version>1.1.18</version>
    </dependency>
    <!--mysql-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.16</version>
    </dependency>
    <!--mybatis-->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>2.1.4</version>
    </dependency>
</dependencies>
```

### 3.修改配置文件

```yml
#配置端口,项目名,开启jsp模板开发
server:
  port: 8081
  servlet:
    context-path: /ems-jsp
    jsp:
      init-parameters:
        development: true
        
#配置jsp前缀后缀,数据库
spring:
  mvc:
    view:
      prefix: /
      suffix: .jsp
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/ems-jsp?characterEncoding=UTF-8&serverTimezone=GMT
    username: root
    password: root

#配置mybatis
mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.xx.entity

#配置日志
logging:
  level:
    root: info
    com.xx: debug
```

### 4.添加静态资源

<img src="C:\Users\xx\AppData\Roaming\Typora\typora-user-images\image-20211211172953916.png" alt="image-20211211172953916" style="zoom:80%;" />

## 3.功能实现

### 1.用户模块功能实现

#### 验证码生成功能实现

1.生成随机字符
2.放入session
3.生成图片并响应在regist.jsp
4.修改regist.jsp

代码:

**controller**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112111733002.png" alt="image-20211211173256739" style="zoom:80%;" />

**regist.jsp**

${pageContext.request.contextPath}:动态生成项目名(ems-jsp)

<img src="C:\Users\xx\AppData\Roaming\Typora\typora-user-images\image-20211211173546538.png" alt="image-20211211173546538" style="zoom:80%;" />

#### 用户注册功能实现

1.根据用户输入验证码比较session中验证码是否一致
2.如果一致完成注册,如果不一致直接返回错误
3.完成注册向数据库中保存当前的用户信息
		a.保存信息之前判断当前用户名是否存在  如果存在直接返回错误
		b.如果当前用户名不存在保存用户信息 保存用户信息给密码进行加密处理

代码:

**修改regist.jsp的form表单**

<img src="C:\Users\xx\AppData\Roaming\Typora\typora-user-images\image-20211212101156295.png" alt="image-20211212101156295" style="zoom:80%;" />

**UserController**

```java
/**
     * 用户注册
     * @param user  表单数据
     * @param code  用户输入的验证码
     * @param session   生成的验证码
     * @return  成功重定向到登录页面,失败重定向到注册页面
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("register")
    public String register(User user,String code,HttpSession session) throws UnsupportedEncodingException {
        log.debug("接收到的验证码:{}",code);
        log.debug("用户名:{},真实姓名:{},密码:{},性别:{}",
                user.getUsername(),user.getRealname(),user.getPassword(),user.getGender());
        try {
            //1.比较验证码
            String sessionCode = session.getAttribute("code").toString();
            if (!sessionCode.equalsIgnoreCase(code)) throw  new RuntimeException("验证码错误!");
            //2.完成注册
            userService.register(user);
        } catch (RuntimeException e) {
            e.printStackTrace();
            //在地址后面拿到报错信息到前端显示
            return "redirect:/regist.jsp?msg="+ URLEncoder.encode(e.getMessage(),"UTF-8");
        }
        return "redirect:/login.jsp";

    }
```

**UserMapper**

```java
    /**
     * 用户注册
     * @param user
     */
    void register(User user);
     /**
     * 根据用户名查询
     * @param username
     * @return
     */
    User selectByUserName(String username);
```

**UserService**

```java
    /**
     * 用户注册
     * @param user
     */
    void register(User user);
```

**UserServiceImpl**

```java
    /**
     * 注册
     * @param user
     */
    @Override
    public void register(User user) {
        //1.查询用户是否存在,已存在则报错
        User userName = userMapper.selectByUserName(user.getUsername());
        if (!ObjectUtils.isEmpty(userName)) throw new RuntimeException("用户名已存在!");
        //2.不存在则注册
        //进行密码加密
        String passwordSecret = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(passwordSecret);
        userMapper.register(user);
    }
```

**UserMapper.xml**

```xml
  <sql id="Base_Column_List">
    id, username, realname, `password`, gender
  </sql>

  <!--根据用户名查询用户-->
  <select id="selectByUserName" parameterType="String" resultType="com.xx.entity.User">
    select 
    <include refid="Base_Column_List" />
    from user
    where username = #{username}
  </select>
    <!--注册-->
  <insert id="register"  keyProperty="id" parameterType="com.xx.entity.User" useGeneratedKeys="true">
    insert into user (username, realname, `password`, gender)
    values (#{username,jdbcType=VARCHAR}, #{realname,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, #{gender,jdbcType=TINYINT})
  </insert>
```

#### 用户登录功能实现

​		1.根据用户输入用户名去数据库中查询是否存在改用户名
​		2.如果存在,判断密码是否一致 如果不存在,用户名输入错误
​		3.判断密码根据数据库加密密码与对接收密码进行md5加密之后比较
​				md5: 只要内容相同  多次计算md5 结果一定是一致
​		4.保存用户登录标记到Session中

代码;

**login.jsp**

```jsp
<form action="${pageContext.request.contextPath}/user/login" method="post">
						<table cellpadding="0" cellspacing="0" border="0"
							class="form_table">
							<tr>
								<td valign="middle" align="right">
									用户名:
								</td>
								<td valign="middle" align="left">
									<input type="text" class="inputgri" name="username" />
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									密码:
								</td>
								<td valign="middle" align="left">
									<input type="password" class="inputgri" name="password" />
								</td>
							</tr>
						</table>
						<p>
							<input type="submit" class="button" value="提交 &raquo;" />
							<input type="button" class="button" onclick="location.href='${pageContext.request.contextPath}/regist.jsp'" value="注册 &raquo;" />
						</p>
					</form>
```

**UserController**

```java
    /**
     * 用户登录
     * @param user  表单
     * @param session   登录成功保存姓名到session
     * @return  重定向到列表页面
     */
    @RequestMapping("login")
    public String login(User user,HttpSession session) throws UnsupportedEncodingException {
        log.debug("登录用户名;{},登录密码:{}",user.getUsername(),user.getPassword());
        try {
            //1.进行登录
            User userDB = userService.login(user);
            log.debug("username:{},realname:{}",userDB.getUsername(),userDB.getRealname());
            //2.保存用户名到session
            session.setAttribute("userDB",userDB);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login.jsp?msg="+URLEncoder.encode(e.getMessage(),"UTF-8");
        }
        return "redirect:/empl/list";
    }
```

**UserService**

```java
    /**
     * 用户登录
     * @param user
     */
    User login(User user);
```

**UserServiceImpl**

```java
    /**
     * 用户登录
     * @param user
     */
    @Override
    public User login(User user) {
        //1.根据用户名查询用户是否存在
        User userDB = userMapper.selectByUserName(user.getUsername());
        if (ObjectUtils.isEmpty(userDB)) throw new RuntimeException("用户名输入错误!");
        //2.判断密码是否正常,先加密再比较
        String newPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        if(!userDB.getPassword().equals(newPassword)) throw new RuntimeException("密码错误!");
        return userDB;
    }
```

根据用户名查询注册时写过,这里直接用!!

### 2.员工模块功能实现

#### 员工列表

​		1.在数据库中查询所有员工信息
​		2.在页面中进行展示

**EmplController**

```java
    /**
     * 员工列表
     * @param model
     * @return
     */
    @RequestMapping("list")
    public String listEmployee(Model model){
        //获取员工列表
        List<Empl> emplList = emplService.list();
        model.addAttribute("emplList",emplList);
        return "emplist";//forward跳转
    }
```

**EmplService**

```java
    /**
     * 员工列表
     * @return
     */
    List<Empl> list();
```

**EmplServiceImpl**

```java
    /**
     * 员工列表
     * @return
     */
    @Override
    public List<Empl> list() {
        return emplMapper.list();
    }
```

**EmplMapper.xml**

```xml
  <!--员工列表-->
    <select id="list" resultType="com.xx.entity.Empl">
      select
      <include refid="Base_Column_List" />
      from `employee`
    </select>
```

**emplist.jsp**

```jsp
 <table class="table">
                <tr class="table_header">
                    <td>
                        ID
                    </td>
                    <td>
                        Name
                    </td>
                    <td>
                        Salary
                    </td>
                    <td>
                        Birthday
                    </td>
                    <td>
                        Gender
                    </td>
                    <td>
                        Operation
                    </td>
                </tr>
                <c:forEach items="${requestScope.emplList}" var="employee" varStatus="sta">
                    <tr
                            <c:if test="${sta.index%2==0}">
                                class="row1"
                            </c:if>
                            <c:if test="${sta.index%2!=0}">
                                class="row2"
                            </c:if>
                    >
                        <td>${employee.id}</td>
                        <td>${employee.name}</td>
                        <td>${employee.salary}</td>
                        <td><fmt:formatDate value="${employee.birthday}" pattern="yyyy-MM-dd"/></td>
                        <td>${employee.gender?'男':'女'}</td>
                        <td>
                            <a href="javascript:;" onclick="deleteEmployee()">删除</a>
                            <script>
                                function deleteEmployee() {
                                    if (window.confirm('确定要删除这条记录吗?')) {
                                        location.href = '${pageContext.request.contextPath}/empl/delete?id=${employee.id}';
                                    }
                                }
                            </script>
                            &nbsp;<a href="${pageContext.request.contextPath}/empl/detail?id=${employee.id}">更新</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
```

总结:引入JSTL依赖,把查询的数据放入ruquest域中(springMVC中可以放入Model中,两者没区别),在前端页面用c标签foreach遍历,日期格式处理用JSTL的fmt:formatDate

#### 添加员工

​	1.在员工controller中开发一个添加方法
​	2.接收员工信息
​	3.将员工信息保存到数据库
​	4.跳转到员工列表展示数据

**addEmpl.jsp**

```jsp
<form action="${pageContext.request.contextPath}/empl/add" method="post">
						<table cellpadding="0" cellspacing="0" border="0"
							class="form_table">
							<tr>
								<td valign="middle" align="right">
									姓名:
								</td>
								<td valign="middle" align="left">
									<input type="text" class="inputgri" name="name" />
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									工资:
								</td>
								<td valign="middle" align="left">
									<input type="text" class="inputgri" name="salary" />
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									生日:
								</td>
								<td valign="middle" align="left">
									<input type="text" class="inputgri" name="birthday" />
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									性别:
								</td>
								<td valign="middle" align="left">
									<select name="gender">
										<option value="1">男</option>
										<option value="0">女</option>
									</select>
								</td>
							</tr>
						</table>
						<p>
							<input type="submit" class="button" value="提交" />
						</p>
					</form>
```

**EmplController**

```java
    /**
     * 添加员工信息
     * @return
     */
    @RequestMapping("add")
    public String addEmpl(Empl empl){
        log.debug("员工姓名:{},工资:{},生日:{},性别:{}",
                empl.getName(),empl.getSalary(),empl.getBirthday(),empl.getGender());
        //保存员工
        emplService.add(empl);
        return "redirect:/empl/list";
    }
```

**EmplMapper**

```java
    /**
     * 添加员工
     * @param empl
     * @return
     */
    void add(Empl empl);
```

**EmplMapper.cml**

```xml
  <!--添加员工-->
  <insert id="add" keyColumn="id" keyProperty="id" parameterType="com.xx.entity.Empl" useGeneratedKeys="true">
    insert into employee (`name`, birthday, salary, gender)
    values (#{name,jdbcType=VARCHAR}, #{birthday,jdbcType=TIMESTAMP}, #{salary,jdbcType=DOUBLE}, 
      #{gender,jdbcType=TINYINT})
  </insert>
```

//springboot springmvc spring 默认处理日期格式:yyyy/MM/dd HH:mm:ss
//@DateTimeFormat(pattern = "yyyy-MM-dd")

总结:填写表单注意日期格式,如果不是默认的,要在实体类,该字段上加上@DateTimeFormat(pattern = "yyyy-MM-dd")





#### 更新员工

​	上半步 数据回显:
​		1.点击更新链接传递当前更新员工id到controller
​		2.在controller中根据id查询员工信息
​		3.将查询到的员工信息保存到作用域
​		4.跳转到更新页面展示修改员工信息 

​	下半步 更新数据:
​	1.获取更新之后员工信息
​	2.更新数据库

**updateEmp.jsp**

```jsp
            <form action="${pageContext.request.contextPath}/empl/update" method="post">
                <table cellpadding="0" cellspacing="0" border="0"
                       class="form_table">
                    <tr>
                        <td valign="middle" align="right">
                            id:
                        </td>
                        <td valign="middle" align="left">
                            <input value="${empl.id}" name="id" readonly/>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right">
                            姓名:
                        </td>
                        <td valign="middle" align="left">
                            <input type="text" class="inputgri" name="name" value="${empl.name}"/>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right">
                            工资:
                        </td>
                        <td valign="middle" align="left">
                            <input type="text" class="inputgri" name="salary" value="${empl.salary}"/>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right">
                            生日:
                        </td>
                        <td valign="middle" align="left">
                            <input type="date" class="inputgri" name="birthday"
                                   value="<fmt:formatDate value="${empl.birthday}" pattern="yyyy-MM-dd"/>"/>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right">
                            性别:
                        </td>
                        <td valign="middle" align="left">
                            <select name="gender">

                                <option value="1"
                                        <c:if test="${empl.gender}">
                                            selected
                                        </c:if>
                                >男
                                </option>

                                <option value="0"
                                        <c:if test="${!empl.gender}">
                                            selected
                                        </c:if>
                                >女
                                </option>

                            </select>
                        </td>
                    </tr>
                </table>
                <p>
                    <input type="submit" class="button" value="提交"/>
                </p>
            </form>
```

**controller**

```java
    /**
     * 更新员工信息
     * @param empl
     * @return
     */
    @RequestMapping("update")
    public String update(Empl empl){
        //1.更新数据
        emplService.update(empl);
        //2.跳转到员工列表
        return "redirect:/empl/list";
    }

    /**
     * 员工详细信息
     * @return
     */
    @RequestMapping("detail")
    public String detail(Integer id,Model model){
        log.debug("id:{}",id);
        //1.根据id查询员工信息
        Empl empl = emplService.selectById(id);
        //2.将查询到的员工信息保存到作用域
        model.addAttribute("empl",empl);
        //3.跳转到更新页面展示修改员工信息
        return "updateEmp";
    }
```

**EmplService**

```java
    /**
     * 更新员工
     * @param empl
     */
    void update(Empl empl);

    /**
     * 员工列表
     * @return
     */
    List<Empl> list();
```

**EmplMapper.xml**

```xml
  <!--根据id查询-->
  <select id="selectById" parameterType="java.lang.Integer" resultType="com.xx.entity.Empl">
    select 
    <include refid="Base_Column_List" />
    from employee
    where id = #{id,jdbcType=INTEGER}
  </select>
  <!--跟新员工信息-->
  <update id="update" parameterType="com.xx.entity.Empl">
    update employee
    <set>
      <if test="name != null">
        `name` = #{name,jdbcType=VARCHAR},
      </if>
      <if test="birthday != null">
        birthday = #{birthday,jdbcType=TIMESTAMP},
      </if>
      <if test="salary != null">
        salary = #{salary,jdbcType=DOUBLE},
      </if>
      <if test="gender != null">
        gender = #{gender,jdbcType=TINYINT},
      </if>
    </set>
    where id = #{id,jdbcType=INTEGER}
  </update>
```

#### 删除员工信息

​	1.点击删除根据id去数据库中删除指定员工信息
​	2.删除成功之后跳转到列表页面

**emplist.jsp**

```jsp
<td>
     <a href="javascript:;" onclick="deleteEmployee()">删除</a>
     <script>
         function deleteEmployee() {
         	if (window.confirm('确定要删除这条记录吗?')) {
                location.href = '${pageContext.request.contextPath}/empl/delete?id=${empl.id}';
            }
         }
     </script>
     <a href="${pageContext.request.contextPath}/empl/detail?id=${empl.id}">更新</a>
 </td>
```

**EmplController**

```java
    /**
     * 更新员工信息
     * @param empl
     * @return
     */
    @RequestMapping("update")
    public String update(Empl empl){
        //1.更新数据
        emplService.update(empl);
        //2.跳转到员工列表
        return "redirect:/empl/list";
    }
```

**EmplMapper.xml**

```xml
  <!--根据id删除员工-->
    <delete id="deleteById" parameterType="java.lang.Integer">
    delete from employee
    where id = #{id,jdbcType=INTEGER}
  </delete>
```

## 总结

### 日期格式

**方式一:input标签格式是text**

```jsp
<input type="text" class="inputgri" name="birthday" />
```

springboot	springmvc	spring 默认处理日期格式:yyyy/MM/dd HH:mm:ss

用方式一:则在添加数据和修改数据时,在页面上写的时候必须是	**/**	隔开

在列表页面回显数据时可以用JSTL中的**fmt:formatDate**设置成其他格式:

```jsp
<td><fmt:formatDate value="${empl.birthday}" pattern="yyyy-MM-dd"/></td>
```

在更新页面数据回显时:

```jsp
<td valign="middle" align="left">
		<input type="text" class="inputgri" name="birthday" value="<fmt:formatDate value="${employee.birthday}" pattern="yyyy/MM/dd"/>"/>
</td>
```

**方式二:input标签是date格式**

```jsp
<input type="date" class="inputgri" name="birthday"/>
```

在实体类的响应字段上加上注解:**@DateTimeFormat(pattern = "yyyy-MM-dd")**

在更新页面数据回显时要设置成	**-**	隔开	否则不会显示:

```java
<td valign="middle" align="left">
		<input type="text" class="inputgri" name="birthday" value="<fmt:formatDate value="${employee.birthday}" pattern="yyyy-MM-dd"/>"/>
</td>
```



### 完善建议

1.增加分页功能(分页插件pagehelper)

2.用户表的增删查改

3.访问路径拦截,一开始只能访问登录页面,不能直接访问其他页面(拦截器)

4.删除时,管理员不能删(service层取出用户名进行判断)

5.退出登录(清空session)

6.form表单字符串输入格式检查,如(电话号码是否是11位,用户名长度是否合理)

7.改成 RestfulAPI 风格

