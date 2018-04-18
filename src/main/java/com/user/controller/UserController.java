package com.user.controller;

import com.user.model.DO.UserDO;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 李亚卿
 * @Date: Created in 11:18 2018/4/11 0011
 * @Description:
 */

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public String register(String userName, String password, @ModelAttribute UserDO user) {
        return "index";
    }
}
