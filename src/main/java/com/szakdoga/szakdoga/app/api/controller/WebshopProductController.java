package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.UpdateWebshopProduct;
import com.szakdoga.szakdoga.app.dto.WebshopDto;
import com.szakdoga.szakdoga.app.dto.WebshopProductDto;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import com.szakdoga.szakdoga.app.service.ComponentService;
import com.szakdoga.szakdoga.app.service.ProductService;
import com.szakdoga.szakdoga.app.service.WebshopProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webshopproduct")
public class WebshopProductController {

    private final WebshopProductService webshopProductService;

    private final ComponentService componentService;

    private final ProductService productService;

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

    @PostMapping("/{webshopProductId}/webshop/{webshopId}")
    public String addWebshopProductToWebshop(@PathVariable Long webshopId,@PathVariable Long webshopProductId){
        webshopProductService.addWebshopProductToWebshop(webshopId,webshopProductId);
        /*Map<String, Boolean> response = new HashMap<>();
        response.put("WebshopProduct added", Boolean.TRUE);
        return ResponseEntity.ok(response);*/

        return "ok";
    }

    @GetMapping("/teszt/{productId}")
    public List<WebshopProduct> teszt (@PathVariable Long productId){
      return webshopProductService.webshopProductsFromProduct(productId);
    }

    @PutMapping("/update/{webshopProductId}")
    public ResponseEntity<Map<String, Boolean>> updateProduct(@PathVariable @Valid Long webshopProductId, @RequestBody UpdateWebshopProduct updateWebshopProduct){
        webshopProductService.updateWebshopProduct(webshopProductId, updateWebshopProduct);
        Map<String, Boolean> response = new HashMap<>();
        response.put("updated", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
