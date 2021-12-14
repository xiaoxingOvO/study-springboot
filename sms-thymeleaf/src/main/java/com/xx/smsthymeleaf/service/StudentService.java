package com.xx.smsthymeleaf.service;

import com.xx.smsthymeleaf.dto.ResultDTO;
import com.xx.smsthymeleaf.pojo.Student;

import java.util.List;

/**
 * @author xiaoxing
 * @create 2021-12-04 9:13
 */
public interface StudentService {

    /**
     * 注册
     * @param stu
     */
    void register(Student stu);

    ResultDTO insertStudent(Student student);

    /**
     * 员工列表
     * @return
     */
    List<Student> lists();
}
