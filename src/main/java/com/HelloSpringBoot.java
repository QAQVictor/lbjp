package com;

import com.user.model.DO.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
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

    @RequestMapping("/hello")
    public String index(HashMap<String, Object> map) {
        return "/index";
    }

    @RequestMapping("/registerPage")
    public String loginPage() {
        return "/registerPage";
    }


}
