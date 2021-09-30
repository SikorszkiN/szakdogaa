package com.szakdoga.szakdoga.app.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
public class ProductDto {
  /*  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/
    @NotBlank
    private String name;
}
