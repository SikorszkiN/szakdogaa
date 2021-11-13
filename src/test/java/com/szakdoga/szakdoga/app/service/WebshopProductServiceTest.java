package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.UpdateUserData;
import com.szakdoga.szakdoga.app.dto.UpdateWebshopProduct;
import com.szakdoga.szakdoga.app.mapper.WebshopProductMapper;
import com.szakdoga.szakdoga.app.repository.ProductRepository;
import com.szakdoga.szakdoga.app.repository.WebshopProductRepository;
import com.szakdoga.szakdoga.app.repository.WebshopRepository;
import com.szakdoga.szakdoga.app.repository.entity.Component;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WebshopProductServiceTest {

    @Mock
    private WebshopProductRepository webshopProductRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private WebshopRepository webshopRepository;

    @Mock
    private WebshopProductMapper webshopProductMapper;

    @Mock
    private WebScrapeService webScrapeService;

    @InjectMocks
    private WebshopProductService webshopProductService;

    private static WebshopProduct ws1 = null;

    private static WebshopProduct ws2 = null;

    @BeforeEach
    void setup(){
        Webshop emag = new Webshop(1L, "emag", "selector", 1000);
        Webshop edigital = new Webshop(2L, "edigital", "selector", 1100);
        ws1 = new WebshopProduct(1L, "webshop1","emag.hu", 5000,  emag);
        ws2 = new WebshopProduct(2L, "webshop2","emag.hu", 4000, edigital);
        List<WebshopProduct> webshops = new ArrayList<>();
        webshops.add(ws1);
        webshops.add(ws2);
    }

    @Test
    public void deleteWebshopProduct() {
        when(webshopProductRepository.findById(1L)).thenReturn(Optional.of(ws1));
        webshopProductRepository.delete(ws1);
        verify(webshopProductRepository).delete(ws1);
    }

    @Test
    public void findByIdTest(){
        // given
        Mockito.when(webshopProductRepository.findById(1L)).thenReturn(Optional.of(ws1));
        // when
        webshopProductRepository.findById(1L);
        // then
        Mockito.verify(webshopProductRepository).findById(1L);
    }

    @Test
    public void modifyWebshopProduct(){
        // Given
        UpdateWebshopProduct updateWebshopProduct = new UpdateWebshopProduct("tesztMódosítás", "tesztmódosítottlink");
        when(webshopProductRepository.findById(1L)).thenReturn(Optional.of(ws1));

        // When
        webshopProductService.updateWebshopProduct(1L, updateWebshopProduct);

        // Then
        assertEquals("tesztMódosítás",ws1.getName());
        assertEquals("tesztmódosítottlink",ws1.getLink());
        verify(webshopProductRepository).save(ws1);
    }


}
