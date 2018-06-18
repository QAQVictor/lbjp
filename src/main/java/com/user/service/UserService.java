package com.user.service;

import com.user.model.DO.UserDO;

import java.util.Map;

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

    /**
     * 获取活动详情页面的创建人信息
     *
     * @param activityId
     * @return
     */
    Map getByActivityId(String activityId);

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    UserDO getUser(String userId);

    /**
     * 更新用户信息
     * @param user
     * @return 0 失败 1成功
     */
    int updateUser(UserDO user);
}
