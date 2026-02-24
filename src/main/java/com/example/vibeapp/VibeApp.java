package com.example.vibeapp;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.example.vibeapp.post")
@EnableTransactionManagement
@SpringBootApplication
public class VibeApp {

    public static void main(String[] args) {
        SpringApplication.run(VibeApp.class, args);
    }

}
