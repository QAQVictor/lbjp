package com.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 李亚卿
 * @Date: Created in 20:51 2018/4/10 0010
 * @Description: 邮件发送demo
 */

@Controller("/email")
public class Email {

    @Autowired
    public JavaMailSender mailSender;


    @RequestMapping("/sendEmail")
    public String sendEmail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //设置寄件人邮箱
        simpleMailMessage.setFrom("17600305595@163.com");
        //设置邮件主题
        simpleMailMessage.setSubject("spring boot 拉帮结派");
        //设置邮件内容
        simpleMailMessage.setText("欢迎使用拉帮结派");
        //设置收件人邮箱
        simpleMailMessage.setTo("835114021@qq.com");
        //发送邮件
        mailSender.send(simpleMailMessage);
        return "index";
    }
}
