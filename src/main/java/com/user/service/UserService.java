package com.user.service;

import com.user.model.DO.UserDO;

/**
 * @Author: 李亚卿
 * @Date: Created in 11:21 2018/4/11 0011
 * @Description:
 */
public interface UserService {
    /**
     * 添加用户
     *
     * @param user
     */
    void saveUser(UserDO user);

    /**
     * 根据用户信息查找用户
     *
     * @param user
     * @return
     */
    UserDO getUser(UserDO user);
}
