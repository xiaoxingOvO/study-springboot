package com.xx.service.impl;

import com.xx.entity.Empl;
import com.xx.mapper.EmplMapper;
import com.xx.service.EmplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xiaoxing
 * @create 2021-12-12 12:25
 */
@Service
@Transactional
public class EmplServiceImpl implements EmplService {

    private EmplMapper emplMapper;

    @Autowired
    public EmplServiceImpl(EmplMapper emplMapper) {
        this.emplMapper = emplMapper;
    }


    /**
     * 更新员工
     * @param empl
     */
    @Override
    public void update(Empl empl) {
        emplMapper.update(empl);

    }

    /**
     * 员工列表
     * @return
     */
    @Override
    public List<Empl> list() {
        return emplMapper.list();
    }


    /**
     * 添加员工
     * @param empl
     */
    @Override
    public void add(Empl empl) {
        emplMapper.add(empl);

    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @Override
    public Empl selectById(Integer id) {
        Empl empl = emplMapper.selectById(id);
        return empl;
    }

    /**
     * 删除员工
     * @param id
     */
    @Override
    public void delete(Integer id) {
        emplMapper.deleteById(id);
    }
}
