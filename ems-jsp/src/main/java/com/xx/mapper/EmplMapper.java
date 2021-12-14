package com.xx.mapper;

import com.xx.entity.Empl;

import java.util.List;

public interface EmplMapper {
    void deleteById(Integer id);

    /**
     * 添加员工
     * @param empl
     * @return
     */
    void add(Empl empl);

    int insertSelective(Empl record);

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    Empl selectById(Integer id);

    /**
     * 更新员工
     * @param empl
     * @return
     */
    void update(Empl empl);

    int updateByPrimaryKey(Empl record);

    /**
     * 员工列表
     * @return
     */
    List<Empl> list();

}