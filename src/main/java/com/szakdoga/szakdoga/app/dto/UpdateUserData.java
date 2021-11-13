package com.szakdoga.szakdoga.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateUserData {

    private String firstName;

    private String lastName;

    private String email;

    public UpdateUserData(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
