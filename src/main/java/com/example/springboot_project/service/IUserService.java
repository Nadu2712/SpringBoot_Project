package com.example.springboot_project.service;

import com.example.springboot_project.dto.RegistrationRequest;
import com.example.springboot_project.entity.User;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> getAllUsers();
    User registerUser(RegistrationRequest registrationRequest);

    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

    void updateUser(Long id, String firstName, String lastName, String email);
    void deleteUser(Long id);
}