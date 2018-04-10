# lbjpdemo
 毕设“拉帮结派”需要的各种demo
 ### 2018-4-10
  邮件发送的demo，页面跳转的demo
  今天遇到了很多问题，刚刚开始使用springboot，很多东西不太明白。
  1.JavaMailSender总是报空指针异常，application.properties也配置了必要的信息，但是还是没有加载配置文件。网上很多答案也没法解决，后来才知道自己犯了很    基础的错误，我把Email类当做普通的类，什么注解也没加，spring当然不知道给我的JavaMailSender配置属性；<br>
  2.没将springboot启动类放入最外层目录，所以其他的@Controller、@RestController等注解也不会自动扫描，所以在访问的时候访问不到其他Controller；<br>
  3.由于我为了解决问题1，就把发送邮件的代码放到了启动类下执行，成功。后来将启动类放在最外层目录，启动失败，其中启动类和Email类中的
    @RequestMapping("/sendEmail")重复。<br>
  4.在@Controller后添加了控制器别名("/email")但是测试访问localhost:8080/email/sendEmail时提示404，这点应该适合SpringMVC不太一样的地方。<br>
