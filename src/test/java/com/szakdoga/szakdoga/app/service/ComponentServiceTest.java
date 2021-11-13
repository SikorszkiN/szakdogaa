package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.ComponentDto;
import com.szakdoga.szakdoga.app.mapper.ComponentMapper;
import com.szakdoga.szakdoga.app.repository.ComponentRepository;
import com.szakdoga.szakdoga.app.repository.WebshopProductRepository;
import com.szakdoga.szakdoga.app.repository.entity.Component;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ComponentServiceTest {

    private static Component component;

    @Mock
    private ComponentRepository componentRepository;

    @Mock
    private WebshopProductService webshopProductService;

    @Mock
    private WebshopProductRepository webshopProductRepository;

    @Mock
    private ComponentMapper componentMapper;

    @InjectMocks
    private ComponentService componentService;

    private static WebshopProduct ws1 = null;

    private static WebshopProduct ws2 = null;

    @BeforeAll
    static void init() {
        Webshop emag = new Webshop(1L, "emag", "selector", 1000);
        Webshop edigital = new Webshop(2L, "edigital", "selector", 1100);
        ws1 = new WebshopProduct(1L, "webshop1","emag.hu", 5000,  emag);
        ws2 = new WebshopProduct(2L, "webshop2","emag.hu", 4000, edigital);
        List<WebshopProduct> webshops = new ArrayList<>();
        webshops.add(ws1);
        webshops.add(ws2);
        component = new Component(1L, "komponens1", webshops, null);
    }


    @Test
    public void cheapestWebshopTest(){
        // Given
        when(componentRepository.findById(1L)).thenReturn(Optional.of(component));

        // When
        WebshopProduct webshopProduct = componentService.getCheapestWebshopData(1L);

        // Then
        assertEquals(webshopProduct, ws2);
        verify(componentRepository).findById(1L);

    }

    @Test
    public void addWebshopProductToComponentTest(){
        // Given
        WebshopProduct ws3 = new WebshopProduct(3L, "webshop3","emag.hu", 5000);
        when(componentRepository.findById(1L)).thenReturn(Optional.of(component));
        when(webshopProductRepository.findById(3L)).thenReturn(Optional.of(ws3));
        component.setWebshopProducts(new ArrayList<>());

        // When
        componentService.addWebshopProductToComponent(component.getId(),ws3.getId());

        // Then
        assertTrue(component.getWebshopProducts().contains(ws3));
    }
}
