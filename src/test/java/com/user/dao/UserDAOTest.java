package com.user.dao;

import com.HelloSpringBoot;
import com.user.model.DO.UserDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Author: 李亚卿
 * @Date: Created in 11:37 2018/4/11 0011
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDAOTest {

    @Autowired
    UserMapper userDAO;

    @Test
    public void insert() {
        UserDO userDO = new UserDO();
        userDO.setUserName("job");
        userDAO.insert(userDO);
    }
    @Test
    public void get(){
        UserDO user=new UserDO();
        user.setUserName("liyaqing");
        user.setPassword("123456");
        userDAO.get(user);
    }
}