package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.repository.ComponentRepository;
import com.szakdoga.szakdoga.app.repository.WebshopRepository;
import com.szakdoga.szakdoga.app.repository.entity.Component;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootTest
public class ComponentServiceTest {

    @Mock
    private ComponentRepository componentRepository;

    @Mock
    private WebshopService webshopService;

    @Mock
    private WebshopRepository webshopRepository;

    @InjectMocks
    private ComponentService componentService;


    @Test
    public void saveComponentDetailsTest() {
        //Given
        Component component = new Component(1L, "termek1", null, null);

        Mockito.when(componentRepository.save(Mockito.any(Component.class))).thenReturn(component);
       //When

        //Then

    }

    @Test
    public void cheapestWebshopTest(){

        //Given
        Webshop ws1 = new Webshop(1L, "webshop1", 5000, 5, true);
        Webshop ws2 = new Webshop(2L, "webshop2", 6000, 4, true);
        List<Webshop> webshops = new ArrayList<>();
        webshops.add(ws1);
        webshops.add(ws2);
        Component component = new Component(1L, "komponens1", webshops , null);
        componentRepository.save(component);

        //When

        Mockito.when(componentService.getCheapestWebshopData(component.getId())).thenReturn();
        Webshop webshop = componentService.getCheapestWebshopData(1L);


        //Then
      //  assertEquals(webshop, ws1);

    }

}
