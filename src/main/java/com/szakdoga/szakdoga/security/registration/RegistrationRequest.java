package com.szakdoga.szakdoga.security.registration;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class RegistrationRequest {


    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String password;

}
