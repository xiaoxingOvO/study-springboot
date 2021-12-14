package com.xx.service;

import com.xx.entity.Empl;

import java.util.List;

/**
 * @author xiaoxing
 * @create 2021-12-12 12:25
 */
public interface EmplService {

    /**
     * 更新员工
     * @param empl
     */
    void update(Empl empl);

    /**
     * 员工列表
     * @return
     */
    List<Empl> list();

    /**
     * 添加员工
     * @param empl
     */
    void add(Empl empl);

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    Empl selectById(Integer id);

    /**
     * 删除员工
     * @param id
     */
    void delete(Integer id);
}
