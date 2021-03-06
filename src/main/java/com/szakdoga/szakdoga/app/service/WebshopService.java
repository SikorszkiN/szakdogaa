package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.UpdateWebshopData;
import com.szakdoga.szakdoga.app.dto.WebshopDto;
import com.szakdoga.szakdoga.app.exception.NoEntityException;
import com.szakdoga.szakdoga.app.mapper.WebshopMapper;
import com.szakdoga.szakdoga.app.repository.WebshopRepository;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WebshopService {

    private final WebshopRepository webshopRepository;

    private final WebshopMapper webshopMapper;

    public List<Webshop> findAll(){
        return webshopRepository.findAll();
    }

    public Webshop findById(Long webshopId){
       return webshopRepository.findById(webshopId).orElseThrow(() -> new NoEntityException("Webshop Not found"));
    }

    public WebshopDto save(WebshopDto webshopDto){

        return  webshopMapper.WebshopToWebshopDto(webshopRepository.save(webshopMapper.WebshopDtoToWebshop(webshopDto)));
    }

    public void deleteWebshop(Long webshopId){
        Webshop webshop = webshopRepository.findById(webshopId).orElseThrow(()->new NoEntityException("webshop not found!"));

        webshopRepository.delete(webshop);
    }

    public void updateWebshop(Long webshopId, UpdateWebshopData updateWebshopData){
        Webshop webshop = webshopRepository.findById(webshopId).orElseThrow(()->new NoEntityException("webshop not found!"));

        if (updateWebshopData.getName() != null){
            webshop.setName(updateWebshopData.getName());
        }

        if (updateWebshopData.getPriceSelector() != null){
            webshop.setPriceSelector(updateWebshopData.getPriceSelector());
        }

        if (updateWebshopData.getDeliveryPrice() != null){
            webshop.setDeliveryPrice(updateWebshopData.getDeliveryPrice());
        }

        webshopRepository.save(webshop);
    }



}
