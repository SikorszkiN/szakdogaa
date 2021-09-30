package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.exception.NoEntityException;
import com.szakdoga.szakdoga.app.repository.WebshopRepository;
import com.szakdoga.szakdoga.app.repository.entity.Component;
import com.szakdoga.szakdoga.app.repository.ComponentRepository;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ComponentService {

    private final ComponentRepository componentRepository;
    private final WebshopService webshopService;
    private final WebshopRepository webshopRepository;

    public List<Component> findAll(){
        return componentRepository.findAll();
    }

    public Component saveComponent(Component component){
        return componentRepository.save(component);
    }

    public Optional<Component> findById(Long componentId){
        return componentRepository.findById(componentId);
    }

    @Transactional
    public void addWebshopToComponent(Long componentId, Long webshopId){
        Component component = componentRepository.findById(componentId).orElseThrow();
        Webshop webshop = webshopRepository.findById(webshopId).orElseThrow();

        component.getWebshops().add(webshop);
    }

    public void WebshopsToComponent(Long componentId, String name){
        Component component = componentRepository.findById(componentId).orElseThrow();
        List<Webshop> webshopsByName = webshopService.findAllByName(name);

        for(var webshop : webshopsByName){
            component.getWebshops().add(webshop);
        }
    }

    private Integer getCheapestWebshopPrice(List<Webshop> webshops){
       return webshops.stream()
                    .map(Webshop::getPrice)
                    .min(Integer::compareTo)
                    .orElse(0);
    }

    public Webshop getCheapestWebshopData(Long componentId){
        Component component = componentRepository.findById(componentId).orElseThrow(()-> new NoEntityException("Nem található a komponens"));

        return component.getWebshops().stream()
                .filter(Webshop::isAvailability)
                .filter(webshop -> webshop.getPrice() == (getCheapestWebshopPrice(component.getWebshops())))
                .min(Comparator.comparing(Webshop::getDeliveryTime, Integer::compareTo))
                .orElseThrow(() -> new NoEntityException("majd ezt az exceptiont át kell írni"));
    }


}
