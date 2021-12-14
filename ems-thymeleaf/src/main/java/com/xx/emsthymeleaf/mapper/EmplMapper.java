package com.xx.emsthymeleaf.mapper;

import com.xx.emsthymeleaf.pojo.Employee;

import java.util.List;

/**
 * @author xiaoxing
 * @create 2021-12-06 16:13
 */
public interface EmplMapper {

    //员工列表
    List<Employee> lists();

    //保存员工信息
    void save(Employee employee);

    //根据id查询
    Employee findById(Integer id);

    //更新员工信息
    void update(Employee employee);

    //删除员工
    void delete(Integer id);
}
