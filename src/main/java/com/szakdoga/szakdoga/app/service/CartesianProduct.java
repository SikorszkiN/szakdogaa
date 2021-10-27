package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartesianProduct {
    public List<List<WebshopProduct>> product(List<List<WebshopProduct>> lists) {
        List<List<WebshopProduct>> product = new ArrayList<>();

        // We first create a list for each value of the first list
        product(product, new ArrayList<>(), lists);

        return product;
    }

    private void product(List<List<WebshopProduct>> result, List<WebshopProduct> existingTupleToComplete, List<List<WebshopProduct>> valuesToUse) {
        for (WebshopProduct value : valuesToUse.get(0)) {
            List<WebshopProduct> newExisting = new ArrayList<>(existingTupleToComplete);
            newExisting.add(value);

            // If only one column is left
            if (valuesToUse.size() == 1) {
                // We create a new list with the exiting tuple for each value with the value
                // added
                result.add(newExisting);
            } else {
                // If there are still several columns, we go into recursion for each value
                List<List<WebshopProduct>> newValues = new ArrayList<>();
                // We build the next level of values
                for (int i = 1; i < valuesToUse.size(); i++) {
                    newValues.add(valuesToUse.get(i));
                }

                product(result, newExisting, newValues);
            }
        }
    }
    
}
