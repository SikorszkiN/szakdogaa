package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.WebshopProductDto;
import com.szakdoga.szakdoga.app.mapper.WebshopProductMapper;
import com.szakdoga.szakdoga.app.repository.WebshopProductRepository;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import com.szakdoga.szakdoga.app.service.WebshopProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webshopprodcut")
public class WebshopController {

    private final WebshopProductService webshopProductService;

    @PostMapping("/save")
    public ResponseEntity<WebshopProduct> saveWebshop(@RequestBody WebshopProductDto webshopProductDto){
        return ResponseEntity.ok(webshopProductService.saveWebshop(webshopProductDto));
    }
}
