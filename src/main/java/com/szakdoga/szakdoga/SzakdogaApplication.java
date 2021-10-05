package com.szakdoga.szakdoga;

import com.szakdoga.szakdoga.app.repository.ProductRepository;
import com.szakdoga.szakdoga.app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SzakdogaApplication implements ApplicationRunner {

    UserRepository userRepository;

    ProductRepository productRepository;

    @Autowired
    public SzakdogaApplication(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(SzakdogaApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //userRepository.save(new User("Detti", "Daróczi", "sikorszki18@gmail.com"));
        //productRepository.save(new Product("Doboz 20 cm",6000));
    }
}
