package com.user.controller;

import com.user.model.DO.UserDO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 李亚卿
 * @Date: Created in 11:18 2018/4/11 0011
 * @Description:
 */

@Controller
public class UserController {
    //@Autowired
    //private UserDAO userDAO;

    @RequestMapping("/register")
    public String register(UserDO userDO) {
        //userDAO.insert(userDO);
        return "index";
    }
}
