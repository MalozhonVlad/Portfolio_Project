package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String startPage() {
        return "startPage";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(User user,
                                   Model model) {
        User userFromDb = userService.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("message", "User already exists!");
            return "registration";
        }

        userService.save(user);

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model,
                            @PathVariable String code) {

        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("code", "User successfully activated");
        } else  {
            model.addAttribute("code","Activation code is not found");
        }

        return "login";

    }




}
