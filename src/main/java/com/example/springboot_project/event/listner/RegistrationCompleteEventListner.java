package com.example.springboot_project.event.listner;

import com.example.springboot_project.entity.User;
import com.example.springboot_project.event.RegistrationCompleteEvent;
import com.example.springboot_project.token.VerificationTokenService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListner implements ApplicationListener<RegistrationCompleteEvent> {
    private final VerificationTokenService tokenService;
    private final JavaMailSender mailSender;
    private User user;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        user = event.getUser();
        String vToken = UUID.randomUUID().toString();
        tokenService.saveVerificationTokenForUser(user,vToken);
        String url = event.getConfirmationUrl()+"/registration/verifyEmail?token"+vToken;

        try{
            sendVerificationEmail(url);
        }catch (MessagingException | UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
    }

    public void sendVerificationEmail(String url) throws MessagingException,UnsupportedEncodingException{
        String subject = "Email Verification";
        String senderName = "User Verification Service";
        String mailContent = "<p> Hi, "+user.getFirstName()+", </p>"+
                "<p>Thank you for registering with us,"+""+
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\""+url+ "\"> Verify your email to activate your account</a>"+
                "<p>Thank you <br> Users Registration Portal Service";
        emailMessage(subject,senderName,mailContent,mailSender,user);
    }
    private static void emailMessage(String subject, String senderName, String mailContent, JavaMailSender mailSender, User theUser)
                                     throws MessagingException, UnsupportedEncodingException{
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("snadushanth2000@gmail.com",senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent,true);
        mailSender.send(message);
    }
}