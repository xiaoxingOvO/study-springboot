package com.xx.mapper;

import com.xx.pojo.User;
import java.util.List;

/**
 * @author xiaoxing
 * @create 2021-12-10 13:20
 */
public interface UserMapper {

    /**
     * @return
     */
    List<User> findAll();
}
