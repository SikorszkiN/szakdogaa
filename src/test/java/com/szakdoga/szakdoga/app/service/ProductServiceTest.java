package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.mapper.ProductMapper;
import com.szakdoga.szakdoga.app.repository.ComponentRepository;
import com.szakdoga.szakdoga.app.repository.ProductRepository;
import com.szakdoga.szakdoga.app.repository.entity.Component;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

@SpringBootTest
public class ProductServiceTest {

    private static Product product;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ComponentRepository componentRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private CartesianProduct cartesianProduct;

    @InjectMocks
    private ProductService productService;

    @BeforeAll
    static void init(){
        List<Component> components = new ArrayList<>();
        List<WebshopProduct> webshops1 = new ArrayList<>();
        List<WebshopProduct> webshops2 = new ArrayList<>();
        WebshopProduct ws1;
        WebshopProduct ws2;
        WebshopProduct ws3;
        Webshop emag = new Webshop(1L, "emag", "selector", 1000);
        Webshop edigital = new Webshop(2L, "edigital", "selector", 1100);
        ws1 = new WebshopProduct(1L, "emag","emag.hu", 5000, 5, true, emag);
        ws2 = new WebshopProduct(2L, "edigital","edigital.hu", 4000, 5, true, edigital);
        ws3 = new WebshopProduct(3L, "edigital","edigital.hu", 6000, 5, true, edigital);
        webshops1.add(ws1);
        webshops1.add(ws2);
        webshops2.add(ws3);
        product = new Product(1L, "Termek1", components);
        Component c1 = new Component(1L,"komponens1", webshops1, List.of(product));
        Component c2 = new Component(2L, "komponens2", webshops2, List.of(product));
        components.add(c1);
        components.add(c2);

    }

    @Test
    public void getProductPriceTest(){
        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        Integer price = productService.getProductPrice(1L);

        assertEquals(11100, price);
    }

/*    public<V> List<List<V>> product(List<List<V>> lists) {
        List<List<V>> product = new ArrayList<>();

        // We first create a list for each value of the first list
        product(product, new ArrayList<>(), lists);

        return product;
    }

    private<V> void product(List<List<V>> result, List<V> existingTupleToComplete, List<List<V>> valuesToUse) {
        for (V value : valuesToUse.get(0)) {
            List<V> newExisting = new ArrayList<>(existingTupleToComplete);
            newExisting.add(value);

            // If only one column is left
            if (valuesToUse.size() == 1) {
                // We create a new list with the exiting tuple for each value with the value
                // added
                result.add(newExisting);
            } else {
                // If there are still several columns, we go into recursion for each value
                List<List<V>> newValues = new ArrayList<>();
                // We build the next level of values
                for (int i = 1; i < valuesToUse.size(); i++) {
                    newValues.add(valuesToUse.get(i));
                }

                product(result, newExisting, newValues);
            }
        }
    }

    @Test
    public void test(){
        List<String> valami1 = List.of("a","b","c");
        List<String> valami2 = List.of("d","e","f");

        var a = product(List.of(valami1,valami2));

        System.out.println(a);
    }*/

}
