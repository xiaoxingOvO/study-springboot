package com.xx.emsthymeleaf.Controller;

import com.xx.emsthymeleaf.pojo.User;
import com.xx.emsthymeleaf.service.UserService;
import com.xx.emsthymeleaf.utils.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author xiaoxing
 * @create 2021-12-06 13:26
 */
@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger log= LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 安全退出
     * @param session
     * @return
     */
    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/employee/lists";
    }

    //用户登录
    @RequestMapping("login")
    public String login(String username,String password,HttpSession session){
        log.debug("本次登录用户名:{}",username);
        log.debug("本次登录密码:{}",password);
        try {
            //1.调用业务层进行登录
            User user = userService.login(username, password);
            //2.保存用户信息
            session.setAttribute("user",user);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";//登录失败回到登录页面
        }
        return "redirect:/employee/lists"; //登录成功后跳转到查询所有员工信息控制路径
    }
    

    /*
    用户注册
     */
    @RequestMapping("register")
    public String register(User user, String code,HttpSession session){
        log.debug("用户名:{},真实姓名:{},密码:{},性别:{}",
                user.getUsername(),user.getRealname(),user.getPassword(),user.getGender());
        log.debug("用户输入的验证码:{}",code);
        try {
            //1.判断用户输入的验证码和用户输入的验证码是否相等
            String sessionCode = session.getAttribute("code").toString();
            if (!sessionCode.equalsIgnoreCase(code)) throw new RuntimeException("验证码输入错误");
            //2.注册用户
            userService.register(user);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "redirect:/register";    //注册失败回到注册
        }

        return "redirect:/login";   //注册成功,跳转到登录
    }


    /*
    * 生成验证码
    * */
    @RequestMapping("/generateImageCode")
    public void generateImageCode(HttpSession session, HttpServletResponse response) throws IOException {

        //1.生成随机数
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //2.保存到session
        session.setAttribute("code",code);
        //3.根据随机数生成图片&&4.响应图片&&5.设置响应类型
        response.setContentType("image/png");
        ServletOutputStream os = response.getOutputStream();
        VerifyCodeUtils.outputImage(220,60,os,code);

    }

}
