package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.UpdateUserData;
import com.szakdoga.szakdoga.app.mapper.UserMapper;
import com.szakdoga.szakdoga.app.repository.AppUserRepository;
import com.szakdoga.szakdoga.app.repository.ProductRepository;
import com.szakdoga.szakdoga.app.repository.entity.*;
import com.szakdoga.szakdoga.security.registration.token.ConfirmationToken;
import com.szakdoga.szakdoga.security.registration.token.ConfirmationTokenRepository;
import com.szakdoga.szakdoga.security.registration.token.ConfirmationTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AppUserServiceTest {

    private AppUser appUser;

    private Product product;

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
        product = new Product(1L, "Termek1", components);
        Component c1 = new Component(1L,"komponens1", webshops1, List.of(product));
        Component c2 = new Component(2L, "komponens2", webshops2, List.of(product));
        components.add(c1);
        components.add(c2);
        ConfirmationToken confirmationToken = new ConfirmationToken(1L, "asdasd" ,LocalDateTime.MIN, LocalDateTime.MAX, LocalDateTime.now(), appUser);
        appUser = new AppUser(1L, "Keresztnev", "Vezeteknev", "teszt@email.hu", "password", UserRole.ADMIN,false, confirmationToken);
        when(confirmationTokenRepository.findByToken(any())).thenReturn(Optional.of(confirmationToken));
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

    @Test
    public void findAllTest(){
        // given
        Mockito.when(appUserRepository.findAll()).thenReturn(List.of(appUser));
        // when
        appUserService.findAll();
        //then
        Mockito.verify(appUserRepository).findAll();
    }

    @Test
    public void deleteUser() {
        // given
        Mockito.when(appUserRepository.findById(1L)).thenReturn(Optional.of(appUser));

        // when
        appUserService.deleteUser(1L);

        // then
        verify(appUserRepository).delete(appUser);
    }

    @Test
    public void modifyUser(){
        // Given
        UpdateUserData updateUserData = new UpdateUserData("firstname", "tesztvezeteknev", "tesztemail@gmail.com");
        when(appUserRepository.findById(1L)).thenReturn(Optional.of(appUser));

        // When
        appUserService.updateUser(1L, updateUserData);

        // Then
        assertEquals("firstname",appUser.getFirstName());
        assertEquals("tesztvezeteknev",appUser.getLastName());
        assertEquals("tesztemail@gmail.com",appUser.getEmail());
        verify(appUserRepository).save(appUser);
    }

    @Test
    public void addProductToUserTest(){
        // Given
        when(appUserRepository.findById(1L)).thenReturn(Optional.of(appUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        appUser.setProducts(new ArrayList<>());

        // When
        appUserService.saveUserProduct(appUser.getId(), product.getId());

        // Then
        assertTrue(appUser.getProducts().contains(product));
    }

    @Test
    public void changeUserRoleTest(){
        UserRole userRole = UserRole.USER;
        when(appUserRepository.findById(1L)).thenReturn(Optional.of(appUser));

        appUserService.changeRole(appUser.getId(), userRole);

        assertEquals(userRole, appUser.getUserRole());
    }

    @Test
    public void encodePassword(){

        appUserService.encodePasswordAndCreateConfirmationToken(appUser);

        assertNotEquals("password", appUser.getPassword());

    }

    @Test
    public void enableUserTest(){

        appUserService.enableAppUser("tesztemail@gmail.com");

        verify(appUserRepository).enableUser("tesztemail@gmail.com");
    }



}
