package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.ProductDto;
import com.szakdoga.szakdoga.app.exception.ApiRequestException;
import com.szakdoga.szakdoga.app.exception.NoEntityException;
import com.szakdoga.szakdoga.app.mapper.ProductMapper;
import com.szakdoga.szakdoga.app.repository.ComponentRepository;
import com.szakdoga.szakdoga.app.repository.ProductRepository;
import com.szakdoga.szakdoga.app.repository.entity.Component;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ComponentRepository componentRepository;
    private final ProductMapper productMapper;
    private final CartesianProduct cartesianProduct;

    public List<Product> findByNAme(String name){
        return productRepository.findByName(name);
    }

    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new NoEntityException("Product not found!"));
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public ProductDto saveProduct(ProductDto productDto){

            if (productDto.getName().isEmpty()) {
                throw new ApiRequestException("Missing Product Name");
            }
            Product product = productMapper.productDtoToProduct(productDto);

            return productMapper.productToProductDto(productRepository.save(product));
    }

    @Transactional
    public void saveProductComponent(Long productId, Long componentId){
        Product product = productRepository.findById(productId).orElseThrow(()-> new NoEntityException("Nem található a termék"));
        Component component = componentRepository.findById(componentId).orElseThrow(()-> new NoEntityException("Nem található a komponens"));

        product.getComponents().add(component);
    }

    public Integer getProductPrice(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(()-> new NoEntityException("Nem található a termék"));

            List<List<WebshopProduct>> allWebshopProducts = product.getComponents().stream().map(Component::getWebshopProducts).collect(Collectors.toList());

            List<List<WebshopProduct>> cartesianProduct = this.cartesianProduct.product(allWebshopProducts);

            int minimalPrice = Integer.MAX_VALUE;

            for (var webshopProducts : cartesianProduct){
                int currentPrice = webshopProducts.stream().mapToInt(WebshopProduct::getPrice).sum();
                List<Long> addedWebshops = new ArrayList<>();
                for(var webshopProduct : webshopProducts){
                    if (!addedWebshops.contains(webshopProduct.getWebshop().getId())){
                        currentPrice+=webshopProduct.getWebshop().getDeliveryPrice();
                        addedWebshops.add(webshopProduct.getWebshop().getId());
                    }
                }
                if (currentPrice < minimalPrice){
                    minimalPrice = currentPrice;
                }
            }
            return minimalPrice;

    }

    public void deleteProduct(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(()-> new NoEntityException("Nem található a termék"));

        productRepository.delete(product);
    }

    public void updateProduct(Long productId, String name){
        Product product = productRepository.findById(productId).orElseThrow(()-> new NoEntityException("Nem található a termék"));
        product.setName(name);
        productRepository.save(product);
    }

}
