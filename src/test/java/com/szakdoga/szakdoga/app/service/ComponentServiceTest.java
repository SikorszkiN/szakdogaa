package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.repository.ComponentRepository;
import com.szakdoga.szakdoga.app.repository.WebshopRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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

    //Given

    //When

    //Then

}
