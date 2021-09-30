package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.repository.ComponentRepository;
import com.szakdoga.szakdoga.app.repository.WebshopRepository;
import com.szakdoga.szakdoga.app.repository.entity.Component;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.print.Book;
import java.util.List;
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
        Component component = new Component(1L, "termek1", null , null);
    }

}
