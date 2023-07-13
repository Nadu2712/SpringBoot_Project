package com.example.springboot_project.token;

import com.example.springboot_project.entity.User;

import java.util.Optional;

public interface IVerificationTokenService {
    String validateToken(String token);
    void saveVerificationTokenForUser(User user, String token);
    Optional<VerificationToken> findByToken(String token);
    void deleteUserToken(Long id);
}
