package com.example.springboot_project.service;

import com.example.springboot_project.Repository.UserRepository;
import com.example.springboot_project.config.EndtoEndUserDetailsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EndtoEndUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(EndtoEndUserDetailsConfig::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
