package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.AppUserDto;
import com.szakdoga.szakdoga.app.exception.ApiRequestException;
import com.szakdoga.szakdoga.app.exception.NoEntityException;
import com.szakdoga.szakdoga.app.mapper.UserMapper;
import com.szakdoga.szakdoga.app.repository.entity.AppUser;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import com.szakdoga.szakdoga.app.repository.ProductRepository;
import com.szakdoga.szakdoga.app.repository.UserRepository;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ProductService productService;

    private final ComponentService componentService;

    private final ProductRepository productRepository;

    private final UserMapper userMapper;

    public List<AppUser> findAll(){
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser){
       boolean userExist = userRepository.findByEmail(appUser.getEmail())
                .isPresent();

       if (userExist) {
           throw new IllegalStateException("email already taken");
       }

       String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

       appUser.setPassword(encodedPassword);

       userRepository.save(appUser);

       return "it works";

    }

    public AppUser saveUser(AppUserDto appUserDto){
        AppUser appUser = userMapper.userDtoToUser(appUserDto);
        return userRepository.save(appUser);
    }

    @Transactional
    public void saveUserProduct(Long userId, Long productId){
        AppUser appUser = userRepository.findById(userId).orElseThrow(()-> new NoEntityException("Nem található a komponens")); // Optional
        Product product = productRepository.findById(productId).orElseThrow(()-> new NoEntityException("Nem található a komponens"));

        appUser.getProductsToUser().add(product);
    }

    public Map<String, Integer> orderedProduct(Long userId){
        AppUser appUser = userRepository.findById(userId).orElseThrow(() -> new ApiRequestException("Nem található ez a felhasználó"));

        Map<String, Integer> nemtom = new HashMap<>();

        for(var p : appUser.getProductsToUser()){
            nemtom.put(p.getName(), productService.getProductPrice(p.getId()));
        }
        return nemtom;
    }

    public String orderedProducts(Long userId){
        AppUser appUser = userRepository.findById(userId).orElseThrow(() -> new ApiRequestException("Nem található ez a felhasználó"));

      //  Map<String, Integer> nemtom = new HashMap<>();
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
    }

}
