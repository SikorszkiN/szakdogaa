package com.szakdoga.szakdoga.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WebshopDto {

    @NotBlank
    private String name;
    @NotBlank
    private String priceSelector;
    @NotBlank
    private Integer deliveryPrice;

}
