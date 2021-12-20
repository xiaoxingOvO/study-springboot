package com.xx.smsthymeleaf.service.impl;

import com.xx.smsthymeleaf.dto.ResultDTO;
import com.xx.smsthymeleaf.mapper.StudentMapper;
import com.xx.smsthymeleaf.pojo.Student;
import com.xx.smsthymeleaf.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author xiaoxing
 * @create 2021-12-04 9:14
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private StudentMapper studentMapper;

    public StudentServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    /**
     * 注册
     *
     * @param
     * @return
     */
    @Override
    public void register(Student stu) {
        //查询该学号是否存在
        Student stuNum = studentMapper.selectByNum(stu.getStuNum());
        //判断是否存在，如果已存在，就抛出异常
        if (!ObjectUtils.isEmpty(stuNum)) throw new RuntimeException("该学号已有学生注册！");
        //如果不在，则可以注册
        studentMapper.insert(stu);
    }

    @Override
    public ResultDTO insertStudent(Student student) {
        studentMapper.insert(student);
        return null;
    }

    /**
     * 学生列表
     *
     * @return
     */
    @Override
    public List<Student> lists() {
        return studentMapper.lists();
    }
}
