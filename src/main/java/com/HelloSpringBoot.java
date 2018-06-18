package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @Author: 李亚卿
 * @Date: Created in 15:30 2018/4/8 0008
 * @Description: spring boot demo
 */
@Controller
@SpringBootApplication

public class HelloSpringBoot {

    @Autowired


    public static void main(String[] args) {
        SpringApplication.run(HelloSpringBoot.class, args);
    }

    /**
     * 注册页面
     *
     * @return
     */
    @RequestMapping("/registerPage")
    public String registerPage() {
        return "/registerPage";
    }

    @RequestMapping("/loginPage")
    public String loginPage() {
        return "/loginPage";
    }

    @RequestMapping("/activityDetail")
    public String activityDetail() {
        return "/activityDetail";
    }

    @RequestMapping("/home")
    public String home() {
        return "/home";
    }

    @RequestMapping("/schedule")
    public String schedule() {
        return "/schedule";
    }

    @RequestMapping("/recently")
    public String recently() {
        return "/recently";
    }

    @RequestMapping("/test")
    public String test() {
        return "/test";
    }
}
