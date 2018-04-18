package com.user.service.impl;

import com.user.model.DO.UserDO;
import com.user.service.UserService;
import com.util.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: 李亚卿
 * @Date: Created in 09:31 2018/4/15 0015
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    public void saveUser() {
        UserDO userDO = new UserDO();
        userDO.setUserName("job");
        userDO.setUserId(DateUtils.getID());
        userService.saveUser(userDO);

    }
}