package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.ComponentPriceCalculate;
import com.szakdoga.szakdoga.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculateService {

    private final ProductRepository productRepository;


    public List<String> componentPriceCalculates(){
        return productRepository.valami();
    }

}
