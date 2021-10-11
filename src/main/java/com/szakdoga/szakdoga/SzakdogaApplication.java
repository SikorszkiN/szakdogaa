package com.szakdoga.szakdoga;

import com.szakdoga.szakdoga.app.repository.ProductRepository;
import com.szakdoga.szakdoga.app.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SzakdogaApplication implements ApplicationRunner {

    AppUserRepository appUserRepository;

    ProductRepository productRepository;

    @Autowired
    public SzakdogaApplication(AppUserRepository appUserRepository, ProductRepository productRepository) {
        this.appUserRepository = appUserRepository;
        this.productRepository = productRepository;
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
