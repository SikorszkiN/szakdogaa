package com.szakdoga.szakdoga.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateWebshopData {

    private String name;
    private String priceSelector;
    private Integer deliveryPrice;
}
