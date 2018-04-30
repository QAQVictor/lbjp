package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

/**
 * @Author: 李亚卿
 * @Date: Created in 15:30 2018/4/8 0008
 * @Description: spring boot demo
 */
@Controller
@SpringBootApplication
@MapperScan("com.user.dao")
public class HelloSpringBoot {

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringBoot.class, args);
    }

    @RequestMapping("/index")
    public String index() {
        return "/index";
    }

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
}
