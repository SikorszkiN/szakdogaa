package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CartesianProductTest {

    @InjectMocks
    CartesianProduct cartesianProduct;

    @Test
    public void test(){

        // Given
        WebshopProduct wp1 = new WebshopProduct(1L,"webshop1", "link1", 4000);
        WebshopProduct wp2 = new WebshopProduct(2L,"webshop2", "link2", 4000);
        WebshopProduct wp3 = new WebshopProduct(3L,"webshop3", "link3", 4000);
        List<WebshopProduct> valami1 = List.of(wp1);
        List<WebshopProduct> valami2 = List.of(wp2,wp3);
        List<List<WebshopProduct>> result = List.of(List.of(wp1, wp2), List.of(wp1, wp3));

        // When
        List<List<WebshopProduct>> a = cartesianProduct.product(List.of(valami1,valami2));

        // Then
        Assertions.assertEquals(result, a);
    }

}
