package com.szakdoga.szakdoga.app.dto;

import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WebshopDto {

    @NotBlank
    private String name;
    private String priceSelector;

}
