package com.user.service.impl;

import com.user.dao.UserMapper;
import com.user.model.DO.UserDO;
import com.user.service.UserService;
import com.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: 李亚卿
 * @Date: Created in 11:21 2018/4/11 0011
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void saveUser(UserDO user) {
        userMapper.insert(user);
    }

    @Override
    public UserDO getUser(UserDO user) {
        return userMapper.get(user);
    }

    @Override
    public Map getByActivityId(String activityId) {
        return userMapper.getByActivityId(activityId);
    }

    @Override
    public UserDO getUser(String userId) {
        return userMapper.getById(userId);
    }

    @Override
    public int updateUser(UserDO user) {
        return userMapper.update(user);
    }
}
