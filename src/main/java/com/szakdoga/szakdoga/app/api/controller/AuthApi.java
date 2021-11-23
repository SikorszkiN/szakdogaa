package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.AuthRequest;
import com.szakdoga.szakdoga.app.repository.entity.AppUser;
import com.szakdoga.szakdoga.app.service.AppUserService;
import com.szakdoga.szakdoga.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/public")
public class AuthApi {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final AppUserService appUserService;

    @PostMapping("login")
    public ResponseEntity<AppUser> login(@RequestBody @Valid AuthRequest request) {

        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            AppUser appUser = (AppUser) authenticate.getPrincipal();
            log.info("User logged in");
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(appUser))
                    .body(appUser);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }




}
