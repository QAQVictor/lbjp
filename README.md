<div align="right">
<img src="https://github.com/QAQVictor/lbjpdemo/blob/master/src/main/resources/static/%E6%8B%89%E5%B8%AE%E7%BB%93%E6%B4%BE.jpeg" width="200" height="200" alt="拉帮结派" style="float:right;"></img>
</div>

# lbjpdemo  
 ## 毕设“拉帮结派”
 ### 2018-7-26
 #### 毕业了，把最后的代码提上去
 > 毕业一个月了，突然想起来忘了提交最后的代码，大学再见！
 
 ### 2018-4-11
 #### springboot+mybatis+mysql整合
 ```
 <!-- Spring Boot Mybatis 依赖 -->
 <dependency>
     <groupId>org.mybatis.spring.boot</groupId>
     <artifactId>mybatis-spring-boot-starter</artifactId>
     <version>1.2.0</version>
 </dependency>

 <!-- MySQL 连接驱动依赖 -->
 <dependency>
     <groupId>mysql</groupId>
     <artifactId>mysql-connector-java</artifactId>
     <version>5.1.41</version>
 </dependency>
 ```
>今天遇到的问题
>>1.使用mybatis，自己添加的xml文件springboot不会自动去扫描，需要自己在properties文件或yml文件中添加配置信息，让springboot去指定路径下加载<br>
>>2.遇到UserMapper没办法自动注入的问题，报出UnsatisfiedDependencyException，网上说的原因有很多：启动类路径，xml文件内容错误，没有添加注解，没有给启动类注明扫描包。后来终于发现问题，UserMapper接口我使用的@Reposity注解，所以报错，具体原因还在查，两个解决方法，一是用@Mapper注解，二是在启动类添加@MapperScan("com.user.dao")，告知spring去哪里扫描dao层文件
 
 ### 2018-4-10
 #### 邮件发送demo
 ```
  <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
  </dependency>
  ```
  #### 页面跳转的demo
  ```
   <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
   </dependency>
  ```
>今天遇到了很多问题，刚刚开始使用springboot，很多东西不太明白。<br>
>>1.JavaMailSender总是报空指针异常，application.properties也配置了必要的信息，但是还是没有加载配置文件。网上很多答案也没法解决，后来才知道自己犯了很基础的错误，我把Email类当做普通的类，什么注解也没加，spring当然不知道给我的JavaMailSender配置属性；<br>
>>2.没将springboot启动类放入最外层目录，所以其他的@Controller、@RestController等注解也不会自动扫描，所以在访问的时候访问不到其他Controller；<br>
>>3.由于我为了解决问题1，就把发送邮件的代码放到了启动类下执行，成功。后来将启动类放在最外层目录，启动失败，其中启动类和Email类中的
    @RequestMapping("/sendEmail")重复。<br>
>>4.在@Controller后添加了控制器别名("/email")但是测试访问localhost:8080/email/sendEmail时提示404，这点应该适合SpringMVC不太一样的地方。<br>
