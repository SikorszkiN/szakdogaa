package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.ComponentDto;
import com.szakdoga.szakdoga.app.exception.ApiRequestException;
import com.szakdoga.szakdoga.app.exception.NoEntityException;
import com.szakdoga.szakdoga.app.mapper.ComponentMapper;
import com.szakdoga.szakdoga.app.repository.ComponentRepository;
import com.szakdoga.szakdoga.app.repository.WebshopProductRepository;
import com.szakdoga.szakdoga.app.repository.entity.Component;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComponentService {

    private final ComponentRepository componentRepository;

    private final WebshopProductService webshopProductService;

    private final WebshopProductRepository webshopProductRepository;

    ComponentMapper componentMapper = new ComponentMapper();


    public List<Component> findAll(){
        return componentRepository.findAll();
    }

    public Component saveComponent(ComponentDto componentDto){
        Component component = componentMapper.componentDtoToComponent(componentDto);
        return componentRepository.save(component);
    }

    public Component findById(Long componentId){
        return componentRepository.findById(componentId).orElseThrow(()-> new NoEntityException("Component not found!"));
    }

    @Transactional
    public void addWebshopProductToComponent(Long componentId, Long webshopId){
        Component component = componentRepository.findById(componentId).orElseThrow(()-> new NoEntityException("Component not found!"));
        WebshopProduct webshopProduct = webshopProductRepository.findById(webshopId).orElseThrow(() -> new NoEntityException("WebshopProduct not found!"));

        component.getWebshopProducts().add(webshopProduct);
    }

    public WebshopProduct getCheapestWebshopData(Long componentId){
        Component component = componentRepository.findById(componentId).orElseThrow(()-> new ApiRequestException("Component not found!"));
        int minPrice = Integer.MAX_VALUE;
        WebshopProduct cheapestWebshopProduct = new WebshopProduct();
        int currentPrice = 0;
        for (WebshopProduct webshopProduct : component.getWebshopProducts()) {
            currentPrice = webshopProduct.getPrice() + webshopProduct.getWebshop().getDeliveryPrice();
            if (currentPrice < minPrice) {
                minPrice = currentPrice;
                cheapestWebshopProduct = webshopProduct;
            }
        }
        return cheapestWebshopProduct;
    }

    public void deleteComponent(Long componentId){
        Component component = componentRepository.findById(componentId).orElseThrow(()-> new ApiRequestException("Component not found!"));

        List<Product> products = component.getProducts();
        for (var product : products){
            product.getComponents().remove(component);
        }

        componentRepository.delete(component);
    }

    public void updateComponent(Long componentId, String name){
        Component component = componentRepository.findById(componentId).orElseThrow(()-> new ApiRequestException("Component not found!"));
        component.setName(name);
        componentRepository.save(component);
    }
}
