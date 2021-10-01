package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.ComponentDto;
import com.szakdoga.szakdoga.app.mapper.ComponentMapper;
import com.szakdoga.szakdoga.app.repository.ComponentRepository;
import com.szakdoga.szakdoga.app.repository.WebshopRepository;
import com.szakdoga.szakdoga.app.repository.entity.Component;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private WebshopService webshopService;

    @Mock
    private WebshopRepository webshopRepository;

    @Mock
    private ComponentMapper componentMapper;

    @InjectMocks
    private ComponentService componentService;

    private static Webshop ws1 = null;

    private static Webshop ws2 = null;

    @BeforeAll
    static void init() {
        ws1 = new Webshop(1L, "webshop1", 5000, 5, true);
        ws2 = new Webshop(2L, "webshop2", 4000, 5, true);
        List<Webshop> webshops = new ArrayList<>();
        webshops.add(ws1);
        webshops.add(ws2);
        component = new Component(1L, "komponens1", webshops, null);
    }



    @Test
    public void saveComponentDetailsTest() {
        //Given
        Component component = new Component(1L, "termek1", null, null);
        ComponentDto componentDto = componentMapper.componentToComponentDto(component);

       //When
        when(componentService.saveComponent(componentDto)).thenReturn(component);
        //Then
        verify(componentRepository).save(component);
    }

    @Test
    public void cheapestWebshopTest(){
        
        when(componentRepository.findById(1L)).thenReturn(Optional.of(component));

        Webshop webshop = componentService.getCheapestWebshopData(1L);

        assertEquals(webshop, ws2);
        verify(componentRepository).findById(1L);

    }
}
