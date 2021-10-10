package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.WebshopProductDto;
import com.szakdoga.szakdoga.app.mapper.WebshopProductMapper;
import com.szakdoga.szakdoga.app.repository.WebshopProductRepository;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import com.szakdoga.szakdoga.app.dto.Selectors;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WebshopProductService {

    private final WebshopProductRepository webshopProductRepository;

    private final WebshopProductMapper webshopProductMapper;

    private final Selectors selectors;

    private final WebScrapeService webScrapeService;

    public WebshopProduct saveWebshop(WebshopProductDto webshopProductDto){
        WebshopProduct webshopProduct = webshopProductMapper.webshopProductDtoToWebshopProduct(webshopProductDto);
        if (webshopProduct.getName().equals("emag")) {
            webshopProduct.setPrice(webScrapeService.getPrice(webshopProductDto.getLink(), selectors.getEmagSelector()));
        }
        if (webshopProduct.getName().equals("edigital")){
            webshopProduct.setPrice(webScrapeService.getPrice(webshopProductDto.getLink(), selectors.getEDigital()));
        }
        if (webshopProduct.getName().equals("golddekor")){
            webshopProduct.setPrice(webScrapeService.getPrice(webshopProductDto.getLink(), selectors.getGoldDekorSelector()));
        }
        return webshopProductRepository.save(webshopProduct);
    }

    public Optional<WebshopProduct> findById(Long id){
        return webshopProductRepository.findById(id);
    }

    public List<WebshopProduct> findAllByName(String name){
        return webshopProductRepository.findAllByName(name);
    }

}
