package com.szakdoga.szakdoga.app.mapper;

import com.szakdoga.szakdoga.app.dto.WebshopDto;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WebshopMapper {

    private ModelMapper modelMapper;

    public WebshopDto webshopToWebshopDtop(Webshop webshop){
        WebshopDto webshopDto = new WebshopDto();

        webshopDto.setName(webshop.getName());
        webshopDto.setAvailability(webshop.isAvailability());
        webshopDto.setPrice(webshopDto.getPrice());
        webshopDto.setDeliveryTime(webshop.getDeliveryTime());

        return webshopDto;
       // return modelMapper.map(webshop, WebshopDto.class);
    }

    public Webshop webshopDtoToWebshop(WebshopDto webshopDto){
       Webshop webshop = new Webshop();

       webshop.setName(webshopDto.getName());
       webshop.setAvailability(webshopDto.isAvailability());
       webshop.setPrice(webshopDto.getPrice());
       webshop.setDeliveryTime(webshopDto.getDeliveryTime());

       return webshop;
    //        return modelMapper.map(webshopDto, Webshop.class);
    }

}
