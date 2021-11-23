package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private final AppUserService appUserService;

    @GetMapping("/{appUserId}")
    public ResponseEntity<Map<String, Boolean>> sendCalculation(@PathVariable @Valid Long appUserId, @RequestBody String email){
        log.info("Sending email!");
        appUserService.sendOrderCalculation(appUserId, email, "Calculation");
        Map<String, Boolean> response = new HashMap<>();
        response.put("Email sent", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
