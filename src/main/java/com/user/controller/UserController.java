package com.user.controller;

import com.user.model.DO.UserDO;
import com.user.service.UserService;
import com.util.DateUtils;
import com.util.VerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author: 李亚卿
 * @Date: Created in 11:18 2018/4/11 0011
 * @Description:
 */

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/registerPage")
    public String registerPage(HttpServletResponse response, HttpSession session) {
        /*//生成图片验证码，获取答案放入session
        VerificationCode verificationCode = new VerificationCode();
        BufferedImage verificationCodeImg = verificationCode.getBufferImage();
        session.setAttribute("answer", verificationCode.getAnswer());
        //图片输出
        response.setContentType("image/jpeg");
        try {
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(verificationCodeImg, "jpeg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return "/registerPage";
    }

    @RequestMapping("/register")
    public String register(UserDO user, HttpServletRequest request) {
        user.setUserId(DateUtils.getID());
        user.setCreateDate(DateUtils.getNowDate());
        userService.saveUser(user);
        request.getSession().setAttribute("user", user);
        return "/index";
    }

    @RequestMapping("/login")
    public String login(UserDO user, HttpServletRequest request) {
        UserDO userDO = userService.getUser(user);
        if (userDO != null) {
            request.getSession().setAttribute("user", userDO);
            return "redirect:index";
        } else {
            return "loginError";
        }
    }

    @RequestMapping("judge")
    public String judge(UserDO user, HttpServletRequest request) {

        return "/index";
    }
}
