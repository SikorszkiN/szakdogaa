package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartesianProduct {
    public List<List<WebshopProduct>> product(List<List<WebshopProduct>> lists) {
        List<List<WebshopProduct>> product = new ArrayList<>();

        product(product, new ArrayList<>(), lists);

        return product;
    }

    private void product(List<List<WebshopProduct>> result, List<WebshopProduct> existingTupleToComplete, List<List<WebshopProduct>> valuesToUse) {
        for (WebshopProduct value : valuesToUse.get(0)) {
            List<WebshopProduct> newExisting = new ArrayList<>(existingTupleToComplete);
            newExisting.add(value);

              if (valuesToUse.size() == 1) {
                result.add(newExisting);
            } else {
                List<List<WebshopProduct>> newValues = new ArrayList<>();

                for (int i = 1; i < valuesToUse.size(); i++) {
                    newValues.add(valuesToUse.get(i));
                }

                product(result, newExisting, newValues);
            }
        }
    }
    
}
