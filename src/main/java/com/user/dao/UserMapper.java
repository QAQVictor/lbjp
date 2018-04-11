package com.user.dao;

import com.user.model.DO.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: 李亚卿
 * @Date: Created in 10:14 2018/4/11 0011
 * @Description:
 */
@Mapper
public interface UserMapper {
    /**
     * 插入新的user
     *
     * @param user
     */
    void insert(UserDO user);
}
