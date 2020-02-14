package com.example.demo.controller;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping
    public String startPage() {
        return "startPage";
    }

    @GetMapping("/registration")
    public String registration() {
        User user = new User();
        User user1 = new User("Sergey", "Hmelya");
        User user2 = new User("Valera", "Valera");

        user.setUsername("1");
        user.setPassword("1");
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);

        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        roles.add(Role.USER);
        user.setAuthorities(roles);

        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);
        list.add(user2);

        userService.save(user);

        System.out.println("-----------");
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost() {

        System.out.println("registrationPost");

        return "redirect:/login";
    }

    @GetMapping("/greeting")
    public String greetingPage() {
        return "greeting";
    }


}
