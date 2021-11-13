package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.UpdateWebshopData;
import com.szakdoga.szakdoga.app.dto.UpdateWebshopProduct;
import com.szakdoga.szakdoga.app.exception.NoEntityException;
import com.szakdoga.szakdoga.app.mapper.WebshopMapper;
import com.szakdoga.szakdoga.app.repository.WebshopRepository;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WebshopServiceTest {

    @Mock
    private WebshopRepository webshopRepository;

    @Mock
    private WebshopMapper webshopMapper;

    @InjectMocks
    private WebshopService webshopService;

    Webshop emag;

    @BeforeEach
    void setup(){
        emag = new Webshop(1L, "emag", "selector", 1000);
    }

    @Test
    public void deleteWebshop() {
        // given
        when(webshopRepository.findById(1L)).thenReturn(Optional.of(emag));
        // when
        webshopRepository.delete(emag);
        // then
        verify(webshopRepository).delete(emag);
    }

    @Test
    public void findByIdTest(){
        // given
        Mockito.when(webshopRepository.findById(1L)).thenReturn(Optional.of(emag));
        // when
        webshopRepository.findById(1L);
        // then
        Mockito.verify(webshopRepository).findById(1L);
    }

    @Test
    public void findAllTest(){
        // given
        Mockito.when(webshopRepository.findAll()).thenReturn(List.of(emag));
        // when
        webshopRepository.findAll();
        //then
        Mockito.verify(webshopRepository).findAll();
    }

    @Test
    public void modifyWebshopProduct(){
        // Given
        UpdateWebshopData updateWebshop = new UpdateWebshopData("tesztNev", null, 8000);
        when(webshopRepository.findById(1L)).thenReturn(Optional.of(emag));

        // When
        webshopService.updateWebshop(1L, updateWebshop);

        // Then
        assertEquals("tesztNev",emag.getName());
        assertEquals("selector",emag.getPriceSelector());
        assertEquals(8000, emag.getDeliveryPrice());
        verify(webshopRepository).save(emag);
    }

}
