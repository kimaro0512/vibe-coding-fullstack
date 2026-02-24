package com.example.vibeapp;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.vibeapp.post")
@SpringBootApplication
public class VibeApp {

    public static void main(String[] args) {
        SpringApplication.run(VibeApp.class, args);
    }

}
