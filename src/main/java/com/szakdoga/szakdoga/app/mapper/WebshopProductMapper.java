package com.szakdoga.szakdoga.app.mapper;

import com.szakdoga.szakdoga.app.dto.WebshopProductDto;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import org.springframework.stereotype.Component;

@Component
public class WebshopProductMapper {

    public WebshopProductDto webshopProductToWebshopProductDto(WebshopProduct webshopProduct){
        WebshopProductDto webshopProductDto = new WebshopProductDto();

        webshopProductDto.setName(webshopProduct.getName());
        webshopProductDto.setLink(webshopProduct.getLink());
       /* webshop.setPrice(webshopDto.getPrice());*/

        return webshopProductDto;
    }

    public WebshopProduct webshopProductDtoToWebshopProduct(WebshopProductDto webshopProductDto){
       WebshopProduct webshopProduct = new WebshopProduct();

       webshopProduct.setName(webshopProductDto.getName());
       webshopProduct.setLink(webshopProductDto.getLink());
       /*webshop.setPrice(webshopDto.getPrice());*/

       return webshopProduct;
    }

}
