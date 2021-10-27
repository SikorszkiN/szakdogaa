package com.szakdoga.szakdoga.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ComponentDto {

    @NotBlank
    private String name;

}
