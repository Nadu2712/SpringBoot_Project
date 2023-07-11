package com.example.springboot_project.dto;

import com.example.springboot_project.entity.Role;
import lombok.Data;

import java.util.Collection;

@Data
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Collection<Role> roles;
}