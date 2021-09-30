package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    @GetMapping()
    public String email(){
        emailService.sendMessage("sikorszki18@gmail.com");
        return "Talán küldtem egy e-mailt";
    }

}
