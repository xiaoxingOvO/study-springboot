package com.xx.emsthymeleaf.service.impl;

import com.xx.emsthymeleaf.mapper.EmplMapper;
import com.xx.emsthymeleaf.pojo.Employee;
import com.xx.emsthymeleaf.service.EmplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xiaoxing
 * @create 2021-12-06 16:20
 */
@Service
@Transactional
public class EmplServiceImpl implements EmplService {

    private EmplMapper emplMapper;

    @Autowired
    public EmplServiceImpl(EmplMapper emplMapper) {
        this.emplMapper = emplMapper;
    }

    //员工列表
    @Override
    public List<Employee> lists() {
        return emplMapper.lists();
    }

    //保存员工信息
    @Override
    public void save(Employee employee) {
        emplMapper.save(employee);
    }

    //根据id查询
    @Override
    public Employee findById(Integer id) {

        return emplMapper.findById(id);
    }

    //更新
    @Override
    public void update(Employee employee) {
        emplMapper.update(employee);
    }

    //删除
    @Override
    public void delete(Integer id) {
        emplMapper.delete(id);
    }
}
