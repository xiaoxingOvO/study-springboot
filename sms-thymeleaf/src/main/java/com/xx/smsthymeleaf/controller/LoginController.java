package com.xx.smsthymeleaf.controller;

import com.xx.smsthymeleaf.dto.ResultDTO;
import com.xx.smsthymeleaf.pojo.Student;
import com.xx.smsthymeleaf.service.StudentService;
import com.xx.smsthymeleaf.utils.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author xiaoxing
 * @create 2021-12-06 9:22
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    private StudentService studentService;

    private static final Logger log = LoggerFactory.getLogger(TestController.class);
    @Autowired
    public void StudentController(StudentService studentService) {
        this.studentService = studentService;
    }



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

    /**
     * 注册
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public String register(Student stu, String code, HttpSession session) {
        log.debug("姓名:{},学号,{},密码:{},性别:{},专业:{},入学时间:{},个人简介:{}",stu.getStuName(),stu.getStuNum(),
                stu.getStuPwd(),stu.getStuGender(),stu.getStuMajor(),stu.getStuTime(),stu.getStuRemark());
        try {
            //1.判断验证码是否一致(忽略大小写)
            log.debug("222222222222222222222");
            String sessionCode = session.getAttribute("code").toString();
            log.debug("111111111111111111111");
            if(!sessionCode.equalsIgnoreCase(code)) throw new RuntimeException("验证码错误!");
            log.debug("333333333333333333333333");
            //2.注册用户
            studentService.register(stu);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "redirect:/regist";//注册失败回到注册页面
        }
        return "redirect:/login";//注册成功，跳转到登陆
    }

    /**
     * 添加
     * @param student
     */
    @PostMapping("insert")
    @ResponseBody
    public ResultDTO insertStudent(Student student){
        return studentService.insertStudent(student);
    }
}
