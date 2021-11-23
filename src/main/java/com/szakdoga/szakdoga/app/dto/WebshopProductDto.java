package com.szakdoga.szakdoga.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class WebshopProductDto {

    @NotBlank
    private String name;
    @NotNull
    private String link;

}
