package com.xx.smsthymeleaf.controller;

import com.xx.smsthymeleaf.pojo.Student;
import com.xx.smsthymeleaf.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

/**
 * @author xiaoxing
 * @create 2021-12-04 9:14
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    public void StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 员工列表
     * @return
     */
    @RequestMapping("/lists")
    public String lists(Model model){
        log.debug("查询所有员工信息");
        List<Student> studentList = studentService.lists();
        model.addAttribute("studentList",studentList);
        return "stulist";
    }



}
