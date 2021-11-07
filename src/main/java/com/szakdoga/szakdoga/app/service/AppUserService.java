package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.AppUserDto;
import com.szakdoga.szakdoga.app.exception.ApiRequestException;
import com.szakdoga.szakdoga.app.exception.NoEntityException;
import com.szakdoga.szakdoga.app.mapper.UserMapper;
import com.szakdoga.szakdoga.app.repository.entity.AppUser;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import com.szakdoga.szakdoga.app.repository.ProductRepository;
import com.szakdoga.szakdoga.app.repository.AppUserRepository;
import com.szakdoga.szakdoga.app.repository.entity.UserRole;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import com.szakdoga.szakdoga.security.registration.RegistrationRequest;
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
import java.util.*;


@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";

    private final AppUserRepository appUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ConfirmationTokenService confirmationTokenService;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final ProductService productService;

    private final ComponentService componentService;

    private final ProductRepository productRepository;

    private final UserMapper userMapper;

    private final EmailService emailService;

    public List<AppUser> findAll(){
        return appUserRepository.findAll();
    }

    public AppUser findById(Long appUserId){

        return appUserRepository.findById(appUserId).orElseThrow(() -> new NoEntityException("User not found!"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser){
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
    public void saveUserProduct(Long userId, Long productId){
        AppUser appUser = appUserRepository.findById(userId).orElseThrow(()-> new NoEntityException("User not found!")); // Optional
        Product product = productRepository.findById(productId).orElseThrow(()-> new NoEntityException("Product not found!"));

        appUser.getProducts().add(product);
    }


    private String orderedProductsEmail(Long userId){
        AppUser appUser = appUserRepository.findById(userId).orElseThrow(() -> new ApiRequestException("Nem található ez a felhasználó"));

        StringBuilder stringBuilder = new StringBuilder();
        for(var p : appUser.getProducts()){
            stringBuilder.append(p.getName()).append(" ")
                    .append(productService.getProductPrice(p.getId()));
        }
       return stringBuilder.toString();
    }

    public void deleteUser(Long appUserId){
        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(()->new NoEntityException("Nem található a felhasználó!"));
        String confirmationToken = appUser.getConfirmationToken().getToken();
        confirmationTokenRepository.delete(confirmationTokenRepository.findByToken(confirmationToken).orElseThrow());
        appUserRepository.delete(appUser);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteUserWithExpiredToke(){
        List<ConfirmationToken> confirmationTokens = confirmationTokenRepository.findAll();

        for(ConfirmationToken confirmationToken : confirmationTokens){
            if(confirmationToken.getExpiresAt().equals(LocalDateTime.now())){
                deleteUser(confirmationToken.getAppUser().getId());
            }
        }
    }

    public void sendOrderCalculation(Long appUserId, String email){
        emailService.sendMessage(email, orderedProductsEmail(appUserId));
    }

    public void changeRole(Long appUserId, UserRole userRole){
        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(()->new NoEntityException("Nem található a felhasználó!"));
        appUser.setUserRole(userRole);

        appUserRepository.save(appUser);
    }

}
