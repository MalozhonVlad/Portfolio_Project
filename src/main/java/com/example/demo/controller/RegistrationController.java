package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

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
    public String registrationPost(@Valid User user,
                                   BindingResult bindingResult, // должен идти перед моделью (Model model)!!!
                                   Model model) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("user", user);

            if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
                model.addAttribute("differentPassword","Passwords are different!");
                return "registration";
            }

            return "registration";
        } else {

            if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
                model.addAttribute("differentPassword","Passwords are different!");
                return "registration";
            }

            User userFromDb = userService.findByUsername(user.getUsername());

            if (userFromDb != null) {
                model.addAttribute("message", "User already exists!");
                return "registration";
            }
            model.addAttribute("user", null);

            userService.save(user);
        }


        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model,
                           @PathVariable String code) {

        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("code", "User successfully activated");
        } else {
            model.addAttribute("code", "Activation code is not found");
        }

        return "login";
    }

    @GetMapping("/resume")
    public String resume() {
        return "resume";
    }

    @GetMapping("/aboutsweater")
    public String aboutsweater() {
        return "aboutsweater";
    }


}
