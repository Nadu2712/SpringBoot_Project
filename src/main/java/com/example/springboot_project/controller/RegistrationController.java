package com.example.springboot_project.controller;

import com.example.springboot_project.dto.RegistrationRequest;
import com.example.springboot_project.entity.User;
import com.example.springboot_project.event.RegistrationCompleteEvent;
import com.example.springboot_project.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final IUserService userService;
    private final ApplicationEventPublisher publisher;
    @GetMapping("/registration-form")
    public String showRegistrationForm(Model model){
        model.addAttribute("user",new RegistrationRequest());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegistrationRequest registrationRequest){
        User user = userService.registerUser(registrationRequest);
        //verification email event
        publisher.publishEvent(new RegistrationCompleteEvent(user,""));
        return "redirect:/registration//registration-form?success";
    }
}
