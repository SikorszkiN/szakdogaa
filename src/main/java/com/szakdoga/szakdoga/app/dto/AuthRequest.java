package com.szakdoga.szakdoga.app.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthRequest {

    @NotNull
    private String email;
    @NotNull
    private String password;
}
