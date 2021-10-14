package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.ProductDto;
import com.szakdoga.szakdoga.app.exception.ApiRequestException;
import com.szakdoga.szakdoga.app.mapper.ProductMapper;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import com.szakdoga.szakdoga.app.service.ProductService;
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
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/findall")
    public ResponseEntity<List<Product>> allProducts(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/findbyid/{productId}")
    public ResponseEntity<Product> findProductById(@PathVariable Long productId){
        try {
            return ResponseEntity.ok(productService.findById(productId));
        }catch (Exception e){
            throw new ApiRequestException("Product not found");
        }
    }

    @GetMapping("/productprice/{productId}")
    public ResponseEntity<Integer> getProductPrice(@PathVariable Long productId){
        return ResponseEntity.ok(productService.getProductPrice(productId));
    }

    @PostMapping("/save")
    public ResponseEntity<ProductDto> saveProduct(@RequestBody @Valid ProductDto productDto){
        return ResponseEntity.ok(productService.saveProduct(productDto));
    }

    @PostMapping("/{productId}/component/{componentId}")
    public void saveProductComponent(@PathVariable Long productId, @PathVariable Long componentId){
        productService.saveProductComponent(productId, componentId);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable @Valid Long productId){
        productService.deleteProduct(productId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
