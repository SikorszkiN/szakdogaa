package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.WebshopDto;
import com.szakdoga.szakdoga.app.mapper.WebshopMapper;
import com.szakdoga.szakdoga.app.repository.WebshopRepository;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WebshopService {

    private final WebshopRepository webshopRepository;

    private final WebshopMapper webshopMapper;

    public Webshop saveWebshop(WebshopDto webshopDto){
        Webshop webshop = webshopMapper.webshopDtoToWebshop(webshopDto);
        return webshopRepository.save(webshop);
    }

    public Optional<Webshop> findById(Long id){
        return webshopRepository.findById(id);
    }

    public List<Webshop> findAllByName(String name){
        return webshopRepository.findAllByName(name);
    }

}
