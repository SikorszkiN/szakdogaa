package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.ProductDto;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import com.szakdoga.szakdoga.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> allProducts(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> findProductById(@PathVariable Long productId){
            return ResponseEntity.ok(productService.findById(productId));
    }

    @GetMapping("/productprice/{productId}")
    public ResponseEntity<Integer> getProductPrice(@PathVariable Long productId){
        return ResponseEntity.ok(productService.getProductPrice(productId));
    }

    @PostMapping("/save")
    public ResponseEntity<ProductDto> saveProduct(@RequestBody @Valid ProductDto productDto){
        log.info("Saving new Product to the database!");
        return ResponseEntity.ok(productService.saveProduct(productDto));
    }

    @PostMapping("/{productId}/components/{componentId}")
    public void saveProductComponent(@PathVariable Long productId, @PathVariable Long componentId){
        productService.saveProductComponent(productId, componentId);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable @Valid Long productId){
        log.info("Delete Product from the database!");
        productService.deleteProduct(productId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Map<String, Boolean>> updateProduct(@PathVariable @Valid Long productId, @RequestBody String name){
        log.info("Modify product!");
        productService.updateProduct(productId, name);
        Map<String, Boolean> response = new HashMap<>();
        response.put("updated", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
