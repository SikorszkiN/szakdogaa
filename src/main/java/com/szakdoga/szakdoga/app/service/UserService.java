package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.UserDto;
import com.szakdoga.szakdoga.app.exception.NoEntityException;
import com.szakdoga.szakdoga.app.mapper.UserMapper;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import com.szakdoga.szakdoga.app.repository.entity.User;
import com.szakdoga.szakdoga.app.repository.ProductRepository;
import com.szakdoga.szakdoga.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ProductRepository productRepository;

    private final UserMapper userMapper;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(User user){
       boolean userExist = userRepository.findByEmail(user.getEmail())
                .isPresent();

       if (userExist) {
           throw new IllegalStateException("email already taken");
       }

       String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

       user.setPassword(encodedPassword);

       userRepository.save(user);

       return "it works";

    }

    public User saveUser(UserDto userDto){

        User user = userMapper.userDtoToUser(userDto);
        return userRepository.save(user);
    }

    @Transactional
    public void saveUserProduct(Long userId, Long productId){
        User user = userRepository.findById(userId).orElseThrow(()-> new NoEntityException("Nem található a komponens")); // Optional
        Product product = productRepository.findById(productId).orElseThrow(()-> new NoEntityException("Nem található a komponens"));

        user.getProductsToUser().add(product);
    }

}
