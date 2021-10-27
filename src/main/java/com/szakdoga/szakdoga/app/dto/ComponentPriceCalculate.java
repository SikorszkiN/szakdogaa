package com.szakdoga.szakdoga.app.dto;

import lombok.Data;

@Data
public class ComponentPriceCalculate {

    private Integer componentPrice;

    private Long webshopProductId;

    public ComponentPriceCalculate(Integer componentPrice, Long webshopProductId) {
        this.componentPrice = componentPrice;
        this.webshopProductId = webshopProductId;
    }
}
