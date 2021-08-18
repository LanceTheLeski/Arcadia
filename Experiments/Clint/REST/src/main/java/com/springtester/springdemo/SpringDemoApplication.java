package com.springtester.springdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.awt.*;

@SpringBootApplication
//@EntityScan("UserProfile")
public class SpringDemoApplication {

    /*@RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/getLogin")
    public String login() {
        return "login stuff";
    }*/

    public static void main(String[] args) throws Exception {

        SpringApplication.run(SpringDemoApplication.class, args);
    }

}
