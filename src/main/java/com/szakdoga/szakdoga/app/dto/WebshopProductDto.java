package com.szakdoga.szakdoga.app.dto;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Data
public class WebshopProductDto {

    @NotBlank
    private String name;
    private String link;
/*    @NotBlank
    private int price;*/
    private int deliveryTime;
    private boolean availability;

}
