package com.szakdoga.szakdoga.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String MESSAGE_FROM;

    private final JavaMailSender javaMailSender;

    public void sendMessage(String to, String email){
        SimpleMailMessage message;
        try {
            message = new SimpleMailMessage();
            message.setFrom(MESSAGE_FROM);
            message.setTo(to);
            message.setSubject("Confirm your email");
            message.setText(email);
            javaMailSender.send(message);

        } catch (Exception e){
            log.error("Hiba az e-mail küldésekor: " + e.getMessage());
        }
    }
}
