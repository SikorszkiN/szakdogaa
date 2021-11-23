package com.szakdoga.szakdoga.app.mapper;

import com.szakdoga.szakdoga.app.dto.ProductDto;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {


    public ProductDto productToProductDto(Product product){
        ProductDto productDto = new ProductDto();

        productDto.setName(product.getName());

        return productDto;
    }

    public Product productDtoToProduct(ProductDto productDto){
        Product product = new Product();

        product.setName(productDto.getName());

        return product;
    }
}
