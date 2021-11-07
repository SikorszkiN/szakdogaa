package com.szakdoga.szakdoga.registration;

import com.szakdoga.szakdoga.app.repository.entity.AppUser;
import com.szakdoga.szakdoga.app.repository.entity.UserRole;
import com.szakdoga.szakdoga.app.service.AppUserService;
import com.szakdoga.szakdoga.app.service.EmailService;
import com.szakdoga.szakdoga.security.registration.RegistrationService;
import com.szakdoga.szakdoga.security.registration.token.ConfirmationTokenRepository;
import com.szakdoga.szakdoga.security.registration.token.ConfirmationTokenService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class RegistrationServiceTest {

    @Mock
    private AppUserService appUserService;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @Mock
    private EmailService emailService;

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    void init(){
        AppUser appUser = new AppUser(1L, "Firstname", "lasname", "email@gmail.com", "password", UserRole.ADMIN);
        Mockito.when(appUserService.findById(any())).thenReturn(appUser);
    }

    @Test
    public void registrationEmailTeszt(){
        String email = "teszt@email.hu";
        boolean isValid = RegistrationService.isValidEmail(email);
        assertTrue(isValid);
    }

}
