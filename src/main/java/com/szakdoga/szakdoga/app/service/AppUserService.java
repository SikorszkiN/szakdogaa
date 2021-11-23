package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.UpdateUserData;
import com.szakdoga.szakdoga.app.exception.ApiRequestException;
import com.szakdoga.szakdoga.app.exception.NoEntityException;
import com.szakdoga.szakdoga.app.repository.AppUserRepository;
import com.szakdoga.szakdoga.app.repository.ProductRepository;
import com.szakdoga.szakdoga.app.repository.entity.AppUser;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import com.szakdoga.szakdoga.app.repository.entity.UserRole;
import com.szakdoga.szakdoga.security.registration.token.ConfirmationToken;
import com.szakdoga.szakdoga.security.registration.token.ConfirmationTokenRepository;
import com.szakdoga.szakdoga.security.registration.token.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";

    private final AppUserRepository appUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ConfirmationTokenService confirmationTokenService;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final ProductService productService;

    private final ProductRepository productRepository;

    private final EmailService emailService;

    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    public AppUser findById(Long appUserId) {

        return appUserRepository.findById(appUserId).orElseThrow(() -> new NoEntityException("User not found!"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
        return new org.springframework.security.core.userdetails.User(
                appUser.getEmail(), appUser.getPassword(), appUser.isEnabled(), true, true,
                true, appUser.getAuthorities());
    }

    public String encodePasswordAndCreateConfirmationToken(AppUser appUser) {
        boolean userExist = appUserRepository.findByEmail(appUser.getEmail()).isPresent();

        if (userExist) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;

    }

    public void enableAppUser(String email) {
        appUserRepository.enableUser(email);
    }


    @Transactional
    public void saveUserProduct(Long userId, Long productId) {
        AppUser appUser = appUserRepository.findById(userId).orElseThrow(() -> new NoEntityException("User not found!"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new NoEntityException("Product not found!"));

        appUser.getProducts().add(product);
    }


    private String orderedProductsEmail(Long userId) {
        AppUser appUser = appUserRepository.findById(userId).orElseThrow(() -> new ApiRequestException("Nem található ez a felhasználó"));

        StringBuilder stringBuilder = new StringBuilder();
        for (var p : appUser.getProducts()) {
            stringBuilder.append(p.getName()).append(": ")
                    .append(productService.getProductPrice(p.getId())).append(" Ft!").append("                             ");
        }
        return stringBuilder.toString();
    }

    public void deleteUser(Long appUserId) {
        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(() -> new NoEntityException("Nem található a felhasználó!"));

        appUserRepository.delete(appUser);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteUserWithExpiredToken() {
        List<ConfirmationToken> confirmationTokens = confirmationTokenRepository.findAll();

        for (ConfirmationToken confirmationToken : confirmationTokens) {
            if (confirmationToken.getExpiresAt().isAfter(LocalDateTime.now())) {
                confirmationTokenRepository.delete(confirmationTokenRepository.findByToken(confirmationToken.getToken()).orElseThrow());
                deleteUser(confirmationToken.getAppUser().getId());
            }
        }
    }

    public void sendOrderCalculation(Long appUserId, String email, String Subject) {
        emailService.sendMessage(email, buildEmail(email, appUserId), "Calculation");
    }

    public void changeRole(Long appUserId, UserRole userRole) {
        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(() -> new NoEntityException("Nem található a felhasználó!"));
        appUser.setUserRole(userRole);

        appUserRepository.save(appUser);
    }

    public void updateUser(Long appUserId, UpdateUserData updateUserData) {
        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(() -> new NoEntityException("Nem található a felhasználó"));

        if (updateUserData.getFirstName() != null) {
            appUser.setFirstName(updateUserData.getFirstName());
        }

        if (updateUserData.getLastName() != null) {
            appUser.setLastName(updateUserData.getLastName());
        }

        if (updateUserData.getEmail() != null) {
            appUser.setEmail(updateUserData.getEmail());
        }
        appUserRepository.save(appUser);
    }

    private String buildEmail(String email, Long appUserId) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + email + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Az alábbi kalkulációt küldték önnek: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> \"" + orderedProductsEmail(appUserId) + "\" </p></blockquote>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}