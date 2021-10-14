package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.WebshopProductDto;
import com.szakdoga.szakdoga.app.mapper.WebshopProductMapper;
import com.szakdoga.szakdoga.app.repository.WebshopProductRepository;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import com.szakdoga.szakdoga.app.service.WebshopProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webshopprodcut")
public class WebshopProductController {

    private final WebshopProductService webshopProductService;

    @PostMapping("/save")
    public ResponseEntity<WebshopProduct> saveWebshop(@RequestBody WebshopProductDto webshopProductDto){
        return ResponseEntity.ok(webshopProductService.saveWebshop(webshopProductDto));
    }

   /* @PostMapping("/{webshopProductId}/webshop/{webshopId}")
    public String saveWebshopToWebshopProduct(@PathVariable @Valid Long webshopProductId, @PathVariable @Valid Long webshopId){
         ResponseEntity.ok(saveWebshopToWebshopProduct(webshopProductId, webshopId));
         return "ok";
    }*/

    @DeleteMapping("/delete/{webshopProductId}")
    public ResponseEntity<Map<String, Boolean>> deleteWebshopProduct(@PathVariable @Valid Long webshopProductId){
        webshopProductService.deleteWebshopProduct(webshopProductId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
