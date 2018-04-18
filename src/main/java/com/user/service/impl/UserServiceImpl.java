package com.user.service.impl;

import com.user.dao.UserMapper;
import com.user.model.DO.UserDO;
import com.user.service.UserService;
import com.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void saveUser(UserDO userDO) {
        userDO.setUserId(DateUtils.getID());
        userMapper.insert(userDO);
    }
}
