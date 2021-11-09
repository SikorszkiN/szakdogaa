package com.szakdoga.szakdoga.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String MESSAGE_FROM;

    private final JavaMailSender javaMailSender;

    public void sendMessage(String to, String email){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setText(email,true);
            helper.setFrom(MESSAGE_FROM);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            javaMailSender.send(message);

        } catch (Exception e){
            log.error("Hiba az e-mail küldésekor: " + e.getMessage());
        }
    }
}
