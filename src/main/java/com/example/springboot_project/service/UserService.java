package com.example.springboot_project.service;

import com.example.springboot_project.Repository.UserRepository;
import com.example.springboot_project.dto.RegistrationRequest;
import com.example.springboot_project.entity.Role;
import com.example.springboot_project.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest registrationRequest) {
        User user = new User(
                registrationRequest.getFirstName(),
                registrationRequest.getLastName(),
                registrationRequest.getEmail(),
                passwordEncoder.encode(registrationRequest.getPassword()),
                List.of(new Role("ROLE_USER"))
        );
        return userRepository.save(user);
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
    }
}
