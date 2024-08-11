package com.storyshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class StoryShareApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoryShareApplication.class, args);
    }

}
