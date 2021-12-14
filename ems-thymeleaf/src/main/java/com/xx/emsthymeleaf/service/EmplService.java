package com.xx.emsthymeleaf.service;

import com.xx.emsthymeleaf.pojo.Employee;

import java.util.List;

/**
 * @author xiaoxing
 * @create 2021-12-06 16:19
 */
public interface EmplService {

    //员工列表
    List<Employee> lists();

    //保存员工信息
    void save(Employee employee);

    //根据id查询
    Employee findById(Integer id);

    //更新员工信息
    void update(Employee employee);

    //删除员工id
    void delete(Integer id);
}
