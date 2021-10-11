package com.szakdoga.szakdoga.app.mapper;

import com.szakdoga.szakdoga.app.dto.WebshopDto;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class WebshopMapper {

    public WebshopDto WebshopToWebshopDto(Webshop webshop){
        WebshopDto webshopDto = new WebshopDto();

        webshopDto.setName(webshop.getName());
        webshopDto.setPriceSelector(webshop.getPriceSelector());

        return webshopDto;
    }

    public Webshop WebshopDtoToWebshop(WebshopDto webshopDto){
        Webshop webshop = new Webshop();

        webshop.setName(webshopDto.getName());
        webshop.setPriceSelector(webshopDto.getPriceSelector());

        return webshop;
    }
}
