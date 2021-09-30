package com.szakdoga.szakdoga.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WebshopDto {
    private Long id;
    @NotBlank
    private String name;
    private int price;
    private int deliveryTime;
    private boolean availability;



}
