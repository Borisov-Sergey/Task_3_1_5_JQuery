package org.example.task_3_1_5.controller;


import org.example.task_3_1_5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.example.task_3_1_5.model.Role;
import org.example.task_3_1_5.model.User;
import org.example.task_3_1_5.service.RegistrationServiceImpl;

import java.util.Collections;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final RegistrationServiceImpl registrationService;

    @Autowired
    public AuthController(UserService userService, RegistrationServiceImpl registrationService) {
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        User userFromDbByName = userService.findByUserName(user.getUserName());
        User userFromDbByEmail = userService.findByEmail(user.getEmail());

        if (userFromDbByName != null || userFromDbByEmail != null) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }

        user.setRoles(Collections.singleton(Role.USER));
        registrationService.register(user);

        return "redirect:/auth/login";
    }
}