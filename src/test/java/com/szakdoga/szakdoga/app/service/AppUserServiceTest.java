package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.mapper.UserMapper;
import com.szakdoga.szakdoga.app.repository.AppUserRepository;
import com.szakdoga.szakdoga.app.repository.ProductRepository;
import com.szakdoga.szakdoga.app.repository.entity.*;
import com.szakdoga.szakdoga.security.registration.token.ConfirmationTokenRepository;
import com.szakdoga.szakdoga.security.registration.token.ConfirmationTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AppUserServiceTest {

    private AppUser appUser;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Mock
    private ProductService productService;

    @Mock
    private ComponentService componentService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AppUserService appUserService;

    @BeforeEach
    void setup(){
        List<Component> components = new ArrayList<>();
        List<WebshopProduct> webshops1 = new ArrayList<>();
        List<WebshopProduct> webshops2 = new ArrayList<>();
        WebshopProduct ws1;
        WebshopProduct ws2;
        WebshopProduct ws3;
        Webshop emag = new Webshop(1L, "emag", "selector", 1000);
        Webshop edigital = new Webshop(2L, "edigital", "selector", 1100);
        ws1 = new WebshopProduct(1L, "emag","emag.hu", 5000, emag);
        ws2 = new WebshopProduct(2L, "edigital","edigital.hu", 4000,  edigital);
        ws3 = new WebshopProduct(3L, "edigital","edigital.hu", 6000,  edigital);
        webshops1.add(ws1);
        webshops1.add(ws2);
        webshops2.add(ws3);
        Product product = new Product(1L, "Termek1", components);
        Component c1 = new Component(1L,"komponens1", webshops1, List.of(product));
        Component c2 = new Component(2L, "komponens2", webshops2, List.of(product));
        components.add(c1);
        components.add(c2);
        appUser = new AppUser(1L, "Keresztnev", "Vezeteknev", "teszt@email.hu", "password", UserRole.ADMIN);
    }

    @Test
    public void findByIdTest(){
        // given
        Mockito.when(appUserRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(appUser));
        // when
        appUserService.findById(1L);
        // then
        Mockito.verify(appUserRepository).findById(1L);
    }

}
