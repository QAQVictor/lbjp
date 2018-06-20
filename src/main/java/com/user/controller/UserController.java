package com.user.controller;

import com.user.model.DO.UserDO;
import com.user.service.UserService;
import com.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    @ResponseBody
    public String judge(UserDO user) {
        if ("".equals(user.getUserName()) || "".equals(user.getPassword()) ||
                "".equals(user.getPhone()) || "".equals(user.getEmail())) {
            return "false";
        }
        if (userService.getUser(user) == null) {
            return "true";
        } else {
            return "false";
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

    @RequestMapping("/getUser")
    @ResponseBody
    public String getUser(String userId, HttpServletRequest request) {
        UserDO user = userService.getUser(userId);
        request.getSession().setAttribute("user", user);
        if ("".equals(user.getSchool()) || "".equals(user.getRealName()) ||
                "".equals(user.getHeadImgPath()) || "".equals(user.getGender())) {
            return "0";
        } else {
            return "1";
        }
    }

    @RequestMapping("updateUser")
    @ResponseBody
    public String updateUser(UserDO user) {
        return String.valueOf(userService.updateUser(user));
    }

    @RequestMapping("/updateUserHeadImg")
    public String updateUserHeadImg(MultipartFile imgInput, String userId, HttpServletRequest request) {
        if (!imgInput.isEmpty()) {
            String tempFileName = imgInput.getOriginalFilename();
            String fileSuffix = tempFileName.substring(tempFileName.lastIndexOf(".") + 1);
            String rootPath = null;
            try {
                rootPath = ResourceUtils.getURL("classpath:").getPath();
                File directory = new File(rootPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String fileName = "headImg" + userId + "." + fileSuffix;
                File path = new File(directory.getAbsolutePath(), "static/img/userheadimg/");
                if (!path.exists()) {
                    path.mkdirs();
                }

                imgInput.transferTo(new File(path.getAbsolutePath() + "/" + fileName));

                //更新用户信息
                UserDO user = new UserDO();
                user.setUserId(userId);
                user.setHeadImgPath("img/userheadimg/" + fileName);
                userService.updateUser(user);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:home";
    }

    @RequestMapping("/initUserInfo")
    @ResponseBody
    public String initUserInfo(String userId) {
        return userService.getUser(userId).toString();
    }

}
