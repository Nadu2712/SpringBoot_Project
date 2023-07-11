package com.example.springboot_project.token;

import com.example.springboot_project.Repository.UserRepository;
import com.example.springboot_project.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class VerificationTokenService implements IVerificationTokenService {
    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    @Override
    public String validateToken(String token) {
        Optional<VerificationToken> theToken  = tokenRepository.findByToken(token);
        if (theToken.isEmpty()){
            return "Invalid verification token";
        }
        User user = theToken.get().getUser();
        Calendar calendar = Calendar.getInstance();
        if (theToken.get().getExpirationTime().getTime()-
                calendar.getTime().getTime() <= 0){
            return "Expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public void saveVerificationTokenForUser(User user, String token) {
        var verificationToken = new VerificationToken(token,user);
        tokenRepository.save(verificationToken);
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
