package com.szakdoga.szakdoga.security.registration;

import com.szakdoga.szakdoga.app.exception.ApiRequestException;
import com.szakdoga.szakdoga.app.repository.AppUserRepository;
import com.szakdoga.szakdoga.app.repository.entity.AppUser;
import com.szakdoga.szakdoga.app.repository.entity.UserRole;
import com.szakdoga.szakdoga.app.service.AppUserService;
import com.szakdoga.szakdoga.app.service.EmailService;
import com.szakdoga.szakdoga.security.registration.token.ConfirmationToken;
import com.szakdoga.szakdoga.security.registration.token.ConfirmationTokenRepository;
import com.szakdoga.szakdoga.security.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.regex.Pattern;


@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;


    private final ConfirmationTokenService confirmationTokenService;

    private final EmailService emailService;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final AppUserRepository appUserRepository;

    public AppUser register(RegistrationRequest request) {
        if (!isValidEmail(request.getEmail())){
            throw new ApiRequestException("Email not valid");
        }

        AppUser appUser = new AppUser(request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                UserRole.ADMIN);

        String token = appUserService.signUpUser(appUser);

        String link = "http://localhost:8080/registration/confirm?token="+ token ;
        emailService.sendMessage(request.getEmail(), buildEmail(request.getFirstName(), link));
        return appUser;
    }

    public static boolean isValidEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        if (email==null){
            return false;
        }
        return pattern.matcher(email).matches();
    }
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        confirmationTokenRepository.delete(confirmationToken);
        return "confirmed";
    }

    private String buildEmail(String name, String link) {
        return "Kedves " + name
                + "!\n\n" + "Regisztrációd megerősítéséhez kérlek kattints a linkre: " + link + "\n\n" +
                "erre 15 perced van különben mindennek vége";
    }



}
