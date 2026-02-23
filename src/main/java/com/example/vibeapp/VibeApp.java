package com.example.vibeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class VibeApp {

    public static void main(String[] args) {
        SpringApplication.run(VibeApp.class, args);
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Hello from Thymeleaf!");
        return "index";
    }

    @GetMapping("/api/hello")
    @ResponseBody
    public String hello() {
        return "Hello, Vibe!";
    }

}
