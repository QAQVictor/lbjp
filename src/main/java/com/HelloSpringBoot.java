package com;

import com.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
public class HelloSpringBoot {

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringBoot.class, args);
    }

    @RequestMapping("/hello")
    public String index(HashMap<String, Object> map) {
        //map.put("hello", "welcome");
        return "/index";
    }

    @RequestMapping("/loginPage")
    public String loginPage() {
        return "loginpage";
    }

    @RequestMapping("/login")
    public String login(String username, String password, HttpSession session, @ModelAttribute User user) {
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        user.setUserName(username);
        user.setPassword(username);
        System.out.println(username);
        return "index";
    }

}
