package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.WebshopDto;
import com.szakdoga.szakdoga.app.mapper.WebshopMapper;
import com.szakdoga.szakdoga.app.repository.WebshopRepository;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebshopService {

    private final WebshopRepository webshopRepository;

    private final WebshopMapper webshopMapper;

    public WebshopDto save(WebshopDto webshopDto){

        return  webshopMapper.WebshopToWebshopDto(webshopRepository.save(webshopMapper.WebshopDtoToWebshop(webshopDto)));
    }

}
