package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.ProductDto;
import com.szakdoga.szakdoga.app.mapper.ProductMapper;
import com.szakdoga.szakdoga.app.repository.entity.Component;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import com.szakdoga.szakdoga.app.repository.ComponentRepository;
import com.szakdoga.szakdoga.app.repository.ProductRepository;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ComponentRepository componentRepository; // componentServiceben lekezelni
    private final ProductMapper productMapper;

    public List<Product> findByNAme(String name){
        return productRepository.findByName(name);
    }

    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow();
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public ProductDto saveProduct(ProductDto productDto){
        //lecsekkolni hogy létezik-e
        if (productRepository.findByName(productDto.getName()).equals(productDto.getName())){
            return productDto;
        }else {
            Product product = productMapper.productDtoToProduct(productDto);
            return productMapper.productToProductDto(productRepository.save(product));
        }
    }

    public void saveProductComponent(Long productId, Long componentId){
        Product product = productRepository.findById(productId).orElseThrow(); //Optional
        Component component = componentRepository.findById(componentId).orElseThrow();

        product.getComponents().add(component);
    }

    //Kiszámolja egy product árát
    public int getProductPrice(Long productId){
        Product product = productRepository.findById(productId).orElseThrow();

        return product.getComponents()
                .stream().map(component -> component.getWebshops()
                        .stream().map(Webshop::getPrice)
                        .min(Integer::compareTo)
                        .orElse(0)).reduce(0, Integer::sum);
    }

    public void deleteProduct(Long productId){
        Product product = productRepository.findById(productId).orElseThrow();

        productRepository.delete(product);
    }

}
