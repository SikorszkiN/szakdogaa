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
import com.szakdoga.szakdoga.security.registration.token.ConfirmationToken;
import com.szakdoga.szakdoga.security.registration.token.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
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

    private final ProductService productService;

    private final ComponentService componentService;

    private final ProductRepository productRepository;

    private final UserMapper userMapper;

    private final EmailService emailService;

    public List<AppUser> findAll(){
        return appUserRepository.findAll();
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

    public AppUser saveUser(AppUserDto appUserDto){
        AppUser appUser = userMapper.userDtoToUser(appUserDto);
        return appUserRepository.save(appUser);
    }

    @Transactional
    public void saveUserProduct(Long userId, Long productId){
        AppUser appUser = appUserRepository.findById(userId).orElseThrow(()-> new NoEntityException("Nem található a komponens")); // Optional
        Product product = productRepository.findById(productId).orElseThrow(()-> new NoEntityException("Nem található a komponens"));

        appUser.getProductsToUser().add(product);
    }

/*    public Map<String, Integer> orderedProduct(Long userId){
        AppUser appUser = appUserRepository.findById(userId).orElseThrow(() -> new ApiRequestException("Nem található ez a felhasználó"));

        Map<String, Integer> nemtom = new HashMap<>();

        for(var p : appUser.getProductsToUser()){
            nemtom.put(p.getName(), productService.getProductPrice(p.getId()));
        }
        return nemtom;
    }

    private String orderedProductsEmail(Long userId){
        AppUser appUser = appUserRepository.findById(userId).orElseThrow(() -> new ApiRequestException("Nem található ez a felhasználó"));

        StringBuilder stringBuilder = new StringBuilder();
        for(var p : appUser.getProductsToUser()){
            stringBuilder.append(p.getName()).append(" ")
                    .append(productService.getProductPrice(p.getId())).append(" Ft az összetevői: ");
                for(var c : p.getComponents()){
                    WebshopProduct webshopProduct = componentService.getCheapestWebshopData(c.getId());
                            stringBuilder.append(c.getName()).append(" Webshop neve: ")
                            .append(webshopProduct.getName()).append(" ")
                            .append(" ")
                            .append(webshopProduct.getPrice()).append(" Ft")
                            .append(" A várható kiszállítási idő: ").append(webshopProduct.getDeliveryTime()).append(" nap ");
                }
        }
       return stringBuilder.toString();
    }*/

    public void deleteUser(Long appUserId){
        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(()->new NoEntityException("Nem található a felhasználó!"));
        appUserRepository.delete(appUser);

    }

/*    public void sendOrderCalculation(Long appUserId){
        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(()->new NoEntityException("Nem található a felhasználó!"));

        emailService.sendMessage(appUser.getEmail(), orderedProductsEmail(appUserId));
    }*/

    public void changeRole(Long appUserId, UserRole userRole){
        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(()->new NoEntityException("Nem található a felhasználó!"));
        appUser.setUserRole(userRole);

        appUserRepository.save(appUser);
    }

}
