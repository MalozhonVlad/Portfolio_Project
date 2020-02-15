package com.example.demo.controller;

import com.example.demo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ApplicationController {


    @GetMapping("/greeting")
    public String greetingPage(@AuthenticationPrincipal User user) {
        System.out.println(user);
        System.out.println("greetingPage");
        return "greeting";
    }

}
