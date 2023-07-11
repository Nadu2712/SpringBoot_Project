package com.example.springboot_project.controller;

import com.example.springboot_project.dto.RegistrationRequest;
import com.example.springboot_project.entity.User;
import com.example.springboot_project.event.RegistrationCompleteEvent;
import com.example.springboot_project.service.IUserService;
import com.example.springboot_project.token.VerificationToken;
import com.example.springboot_project.token.VerificationTokenService;
import com.example.springboot_project.utility.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final IUserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenService tokenService;
    @GetMapping("/registration-form")
    public String showRegistrationForm(Model model){
        model.addAttribute("user",new RegistrationRequest());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegistrationRequest registrationRequest, HttpServletRequest request){
        User user = userService.registerUser(registrationRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user, UrlUtil.getApplicationUrl(request)));
        return "redirect:/registration/registration-form?success";
    }
    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        Optional<VerificationToken> theToken = tokenService.findByToken(token);
        if (theToken.isPresent() && theToken.get().getUser().isEnabled()){
            return "redirect:/login?verified";
        }
        String verificationResult = tokenService.validateToken(String.valueOf(theToken));
        if (verificationResult.equalsIgnoreCase("invalid")){
            return "redirect:/error?invalid";
        }else if (verificationResult.equalsIgnoreCase("expired")){
            return "redirect:/error?expired";
        } else if (verificationResult.equalsIgnoreCase("valid")) {
            return "redirect:/login?valid";
        }
        return "redirect:/error?invalid";
    }
}