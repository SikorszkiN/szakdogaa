package com.szakdoga.szakdoga;

import com.szakdoga.szakdoga.app.repository.ProductRepository;
import com.szakdoga.szakdoga.app.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableScheduling
@SpringBootApplication
public class SzakdogaApplication {

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

}
