package com.xx.smsthymeleaf.mapper;

import com.xx.smsthymeleaf.pojo.Student;
import java.util.List;

public interface StudentMapper {


    int deleteByPrimaryKey(Integer stuId);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer stuId);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    /**
     * 员工列表
     * @return
     */
    List<Student> lists();

    Student selectByNum(String stuNum);



}