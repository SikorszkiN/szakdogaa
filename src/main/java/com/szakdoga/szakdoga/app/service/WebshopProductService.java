package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.WebshopProductDto;
import com.szakdoga.szakdoga.app.exception.NoEntityException;
import com.szakdoga.szakdoga.app.mapper.WebshopProductMapper;
import com.szakdoga.szakdoga.app.repository.WebshopProductRepository;
import com.szakdoga.szakdoga.app.repository.WebshopRepository;
import com.szakdoga.szakdoga.app.dto.Selectors;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WebshopProductService {

    private final WebshopProductRepository webshopProductRepository;

    private final WebshopRepository webshopRepository;

    private final WebshopProductMapper webshopProductMapper;

    private final Selectors selectors;

    private final WebScrapeService webScrapeService;

    public WebshopProduct saveWebshop(WebshopProductDto webshopProductDto){
        WebshopProduct webshopProduct = webshopProductMapper.webshopProductDtoToWebshopProduct(webshopProductDto);
        /*if (webshopProduct.getName().equals("emag")) {
            webshopProduct.setPrice(webScrapeService.getPrice(webshopProductDto.getLink(), selectors.getEmagSelector()));
        }
        if (webshopProduct.getName().equals("edigital")){
            webshopProduct.setPrice(webScrapeService.getPrice(webshopProductDto.getLink(), selectors.getEDigital()));
        }
        if (webshopProduct.getName().equals("golddekor")){
            webshopProduct.setPrice(webScrapeService.getPrice(webshopProductDto.getLink(), selectors.getGoldDekorSelector()));
        }*/
        webshopProduct.setPrice(webScrapeService.getPrice(webshopProduct.getLink(),webshopProduct.getName()));
        return webshopProductRepository.save(webshopProduct);
    }

    public Optional<WebshopProduct> findById(Long id){
        return webshopProductRepository.findById(id);
    }

    public List<WebshopProduct> findAllByName(String name){
        return webshopProductRepository.findAllByName(name);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void refreshWebshopProductPrice(){
        List<WebshopProduct> webshopProducts = webshopProductRepository.findAll();
        WebshopProduct webshopProduct;
        int newPrice;
        for (var product : webshopProducts){
            newPrice = webScrapeService.getPrice(product.getLink(), product.getName());
            webshopProduct = webshopProductRepository.findById(product.getId()).orElseThrow(() -> new NoEntityException("No Entity found"));
            if(newPrice!= product.getPrice()){
                webshopProduct.setPrice(newPrice);
                webshopProductRepository.save(webshopProduct);
            }
        }
       System.out.println("frissítem");
    }

    /*public void WebshopToWebshopProduct(Long webshopId, Long webshopPorudctId){
        WebshopProduct webshopProduct = webshopProductRepository.findById(webshopPorudctId).orElseThrow(() -> new NoEntityException("Nem található a webshopProduct!"));
        Webshop webshop = webshopRepository.findById(webshopId).orElseThrow(() -> new NoEntityException("Nem található a webshop!"));

        webshop.getWebshopProducts().add(webshopProduct);
    }*/

}
