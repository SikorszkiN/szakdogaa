package com.szakdoga.szakdoga.app.mapper;

import com.szakdoga.szakdoga.app.dto.WebshopDto;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import org.springframework.stereotype.Component;

@Component
public class WebshopMapper {

    public WebshopDto WebshopToWebshopDto(Webshop webshop){
        WebshopDto webshopDto = new WebshopDto();

        webshopDto.setName(webshop.getName());
        webshopDto.setPriceSelector(webshop.getPriceSelector());
        webshopDto.setDeliveryPrice(webshop.getDeliveryPrice());

        return webshopDto;
    }

    public Webshop WebshopDtoToWebshop(WebshopDto webshopDto){
        Webshop webshop = new Webshop();

        webshop.setName(webshopDto.getName());
        webshop.setPriceSelector(webshopDto.getPriceSelector());
        webshop.setDeliveryPrice(webshopDto.getDeliveryPrice());

        return webshop;
    }
}
