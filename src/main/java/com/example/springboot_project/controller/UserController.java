package com.example.springboot_project.controller;

import com.example.springboot_project.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
@GetMapping
    public String getUser(Model model){
        model.addAttribute("user", userService.getAllUsers());
        return "users";
    }
}