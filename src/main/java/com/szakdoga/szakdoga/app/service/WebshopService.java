package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.WebshopDto;
import com.szakdoga.szakdoga.app.mapper.WebshopMapper;
import com.szakdoga.szakdoga.app.repository.WebshopRepository;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import com.szakdoga.szakdoga.app.dto.Selectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WebshopService {

    private final WebshopRepository webshopRepository;

    private final WebshopMapper webshopMapper;

    private final Selectors selectors;

    private final WebScrapeService webScrapeService;

    public Webshop saveWebshop(WebshopDto webshopDto){
        Webshop webshop = webshopMapper.webshopDtoToWebshop(webshopDto);
        webshop.setPrice(webScrapeService.getPrice(webshopDto.getLink(), selectors.getEmagSelector()));
        return webshopRepository.save(webshop);
    }

    public Optional<Webshop> findById(Long id){
        return webshopRepository.findById(id);
    }

    public List<Webshop> findAllByName(String name){
        return webshopRepository.findAllByName(name);
    }

}
