package com.user.controller;

import com.user.model.DO.UserDO;
import com.user.service.UserService;
import com.util.DateUtils;
import com.util.VerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 李亚卿
 * @Date: Created in 11:18 2018/4/11 0011
 * @Description:
 */

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 注册
     *
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/register")
    public String register(UserDO user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if ((!"".equals(user.getUserName())) && (!"".equals(user.getPassword())) &&
                (!"".equals(user.getPhone())) && (!"".equals(user.getEmail()))) {
            if (userService.getUser(user) == null) {
                user.setCreateDate(DateUtils.getNowDate());
                user.setUserId(DateUtils.getIDByDate(user.getCreateDate()));
                userService.saveUser(user);
                request.getSession().setAttribute("user", user);
                return "redirect:index";
            } else {
                response.getWriter().print("false");
                return "/registerPage";
            }
        } else {
            response.getWriter().print("false");
            return "/registerPage";
        }
    }


    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(UserDO user, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (user.getEmail() != null && user.getPassword() != null &&
                !"".equals(user.getEmail()) && !"".equals(user.getPassword())) {
            UserDO userDO = userService.getUser(user);
            if (userDO != null) {
                request.getSession().setAttribute("user", userDO);
                map.put("state", "true");
                map.put("userId", userDO.getUserId());
            } else {
                map.put("state", "false");
            }
        } else {
            map.put("state", "false");
        }
        return map;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().setAttribute("user", null);
        return "/index";
    }

    /**
     * @param user
     * @return 返回true表示验证通过，false为不通过
     */
    @RequestMapping("/judge")
    public void judge(UserDO user, HttpServletResponse response) throws IOException {
        if ("".equals(user.getUserName()) || "".equals(user.getPassword()) ||
                "".equals(user.getPhone()) || "".equals(user.getEmail())) {
            response.getWriter().print("false");
            return;
        }
        if (userService.getUser(user) == null) {
            response.getWriter().print("true");
        } else {
            response.getWriter().print("false");
        }
    }

    /**
     * 获取活动详情页面创建人信息
     *
     * @param activityId
     * @return
     */
    @RequestMapping("/getActivityCreator")
    @ResponseBody
    public Map<String, Object> getActivityCreator(String activityId) {
        return userService.getByActivityId(activityId);
    }

}
