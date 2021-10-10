package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.mapper.ProductMapper;
import com.szakdoga.szakdoga.app.repository.ComponentRepository;
import com.szakdoga.szakdoga.app.repository.ProductRepository;
import com.szakdoga.szakdoga.app.repository.entity.Component;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {

    private static Product product;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ComponentRepository componentRepository;
    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeAll
    static void init(){
        List<Component> components = new ArrayList<>();
        List<WebshopProduct> webshops1 = new ArrayList<>();
        List<WebshopProduct> webshops2 = new ArrayList<>();
        WebshopProduct ws1 = new WebshopProduct(1L, "emag","emag.hu", 5000, 5, true);
        WebshopProduct ws2 = new WebshopProduct(2L, "edigital","edigital.hu", 4000, 5, true);
        WebshopProduct ws3 = new WebshopProduct(3L, "edigital","edigital.hu", 6000, 5, true);
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
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        int price = productService.getProductPrice(1L);

        assertEquals(10000, price);
    }

}
