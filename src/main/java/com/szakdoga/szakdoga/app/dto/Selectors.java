package com.szakdoga.szakdoga.app.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Selectors {

    String emagSelector = "nav .product-new-price";
    String goldDekorSelector = "table .product_table_price";
    String eDigital = "article .price-wrapper .price";
    String praktiker = "section .price";

}
