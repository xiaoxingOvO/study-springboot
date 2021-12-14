package com.xx.controller;

import com.xx.entity.User;
import com.xx.mapper.UserMapper;
import com.xx.service.UserService;
import com.xx.utils.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author xiaoxing
 * @create 2021-12-11 17:14
 */
@Controller
@RequestMapping("user")
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

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
            return "redirect:/regist.jsp?msg="+ URLEncoder.encode(e.getMessage(),"UTF-8");
        }
        return "redirect:/login.jsp";

    }

    /**
     * 生成验证码
     */
    @RequestMapping("generateImageCode")
    public void generateImageCode(HttpSession session, HttpServletResponse response) throws IOException {
        //1.生成随机数
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //2.保存数字到session
        session.setAttribute("code",code);
        //3.生成图片并响应
        response.setContentType("image/png");//指定响应类型
        final ServletOutputStream os = response.getOutputStream();
        VerifyCodeUtils.outputImage(220,80,os,code);
    }


}
