package com.szakdoga.szakdoga.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateWebshopProduct {

    private String name;

    private String link;

    public UpdateWebshopProduct(String name, String link) {
        this.name = name;
        this.link = link;
    }
}
