# springboot-thymeleaf小项目

## 1.表结构设计

user表--id(int,主键、自增、无符号)

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061210943.png" alt="image-20211206121025808" style="zoom:80%;" />

employee表--id(int,主键、自增、无符号)

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061226729.png" alt="image-20211206122611681" style="zoom:80%;" />

## 2.环境配置

### 1.创建项目

<img src="C:\Users\xx\AppData\Roaming\Typora\typora-user-images\image-20211206122830941.png" alt="image-20211206122830941" style="zoom:80%;" />

### 2.引入依赖

引入thymeleaf、mybatis、数据库等依赖

<img src="C:\Users\xx\AppData\Roaming\Typora\typora-user-images\image-20211206124230667.png" alt="image-20211206124230667" style="zoom:80%;" />

### 3.配置文件

配置文件中配置端口号、引入的依赖相关配置等

![image-20211206132226190](https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061322251.png)

### 4.测试

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061301236.png" alt="image-20211206130159190" style="zoom:80%;" />

### 5.入口Application文件添加扫描注解扫描pojo包

![image-20211206130505969](https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061305003.png)

### 6.添加静态资源

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061311674.png" alt="image-20211206131149641" style="zoom:80%;" />

![image-20211206131231128](https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061312174.png)

配置完后运行项目访问login,也可以不配置上面,直接在controller中写

## 3.功能实现

### 1.验证码

​		**1.验证码工具类**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061344716.png" alt="image-20211206134456686" style="zoom:80%;" />

​		**2.编写controller**

<img src="C:\Users\xx\AppData\Roaming\Typora\typora-user-images\image-20211206134531128.png" alt="image-20211206134531128" style="zoom:80%;" />

​		**3.regist页面显示验证码-并可以点击换一张**

​					导入css

<img src="C:\Users\xx\AppData\Roaming\Typora\typora-user-images\image-20211206134753645.png" alt="image-20211206134753645" style="zoom:80%;" />

​			修改图片访问路径,点击换一张--绑定script

<img src="C:\Users\xx\AppData\Roaming\Typora\typora-user-images\image-20211206134935405.png" alt="image-20211206134935405" style="zoom:80%;" />

### 2.用户注册

**1.修改注册页面的form表单**

name属性名和实体类对象里的属性名一样,才能对应介绍

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061356924.png" alt="image-20211206135650847" style="zoom:80%;" />

**2.controller**

注入userService

进行验证码判断,验证码正确则开始注册

<img src="C:\Users\xx\AppData\Roaming\Typora\typora-user-images\image-20211206150223623.png" alt="image-20211206150223623" style="zoom:80%;" />

<img src="C:\Users\xx\AppData\Roaming\Typora\typora-user-images\image-20211206150150353.png" alt="image-20211206150150353" style="zoom:80%;" />

**3.service**

查询用户是否存在,不存在就插入用户并对用户密码进行MD5加密

<img src="C:\Users\xx\AppData\Roaming\Typora\typora-user-images\image-20211206150317798.png" alt="image-20211206150317798" style="zoom:80%;" />

![image-20211206151205316](https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061512382.png)

**4.mapper**

写保存用户和查找用户的SQL语句

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061504624.png" alt="image-20211206150430581" style="zoom:80%;" />

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061504863.png" alt="image-20211206150456818" style="zoom:80%;" />

### 3.用户登录

**1.修改登录页面的form表单**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061553377.png" alt="image-20211206155310326" style="zoom:80%;" />

**2.controller**

![image-20211206155345971](https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061553019.png)

**3.service**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061554777.png" alt="image-20211206155413743" style="zoom:80%;" />

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061554691.png" alt="image-20211206155437651" style="zoom:80%;" />

**4.mapper**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061555269.png" alt="image-20211206155512236" style="zoom:80%;" />

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061555161.png" alt="image-20211206155533120" style="zoom:80%;" />

### **4.员工列表**

**1.修改员工列表页面**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061725375.png" alt="image-20211206172512292" style="zoom:80%;" />

**2.controller**

把信息存到Request域中,在员工页面用thymeleaf语法遍历显示

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061725630.png" alt="image-20211206172540568" style="zoom:80%;" />

**3.service**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061726206.png" alt="image-20211206172606164" style="zoom:80%;" />

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061726349.png" alt="image-20211206172616303" style="zoom:80%;" />

**4.mapper**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061726683.png" alt="image-20211206172636645" style="zoom:80%;" />

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061726091.png" alt="image-20211206172646051" style="zoom:80%;" />

### 5.员工添加

**1.修改员工列表页面**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061921608.png" alt="image-20211206192134540" style="zoom:80%;" />

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061917774.png" alt="image-20211206191709719" style="zoom:80%;" />

**2.controller**

<img src="C:\Users\xx\AppData\Roaming\Typora\typora-user-images\image-20211206192311040.png" alt="image-20211206192311040" style="zoom:80%;" />

**3.service**

<img src="C:\Users\xx\AppData\Roaming\Typora\typora-user-images\image-20211206192414653.png" alt="image-20211206192414653" style="zoom:80%;" />

**4.mapper**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061924800.png" alt="image-20211206192438766" style="zoom:80%;" />

**5.配置文件**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112061928461.png" alt="image-20211206192824401" style="zoom:80%;" />

**6.员工的修改**

**1.修改员工列表**

修改按钮的点击地址,让他访问到controller去通过该id查询员工信息,然后回显到修改页面上

注意地址的附带id的写法

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112062024554.png" alt="image-20211206202456511" style="zoom:80%;" />

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112062026955.png" alt="image-20211206202614912" style="zoom:80%;" />

**2.controller**

![image-20211206211434662](https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112062114735.png)

**3.service**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112062119039.png" alt="image-20211206211928004" style="zoom:80%;" />

**4.mapper**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112062119347.png" alt="image-20211206211959312" style="zoom:80%;" />

### 6.员工删除

**1.修改员工列表页面**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112062129942.png" alt="image-20211206212922893" style="zoom:80%;" />

**2.controller**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112062134870.png" alt="image-20211206213453831" style="zoom:80%;" />

**3.service**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112062136258.png" alt="image-20211206213612220" style="zoom:80%;" />

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112062136223.png" alt="image-20211206213629184" style="zoom:80%;" />

**4.mapper**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112062136458.png" alt="image-20211206213647422" style="zoom:80%;" />

### **7.安全退出**

**1.修改员工列表页面地址**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112062139073.png" alt="image-20211206213907038" style="zoom:80%;" />

**2.controller**

<img src="https://cdn.jsdelivr.net/gh/xiaoxingOvO/Typora-picture@master/img/202112062143570.png" alt="image-20211206214306532" style="zoom:80%;" />