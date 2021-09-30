package com.szakdoga.szakdoga.app.mapper;

import com.szakdoga.szakdoga.app.dto.ProductDto;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private ModelMapper modelMapper;

    public ProductDto productToProductDto(Product product){
        ProductDto productDto = new ProductDto();

       // productDto.setId(product.getId());
        productDto.setName(product.getName());

        return productDto;
       // return modelMapper.map(product, ProductDto.class);
    }

    public Product productDtoToProduct(ProductDto productDto){
        Product product = new Product();

      /*  product.setId(productDto.getId());*/
        product.setName(productDto.getName());

        return product;
        //return modelMapper.map(productDto, Product.class);
    }
}
