package com.szakdoga.szakdoga;

import com.szakdoga.szakdoga.app.exception.NoEntityException;
import com.szakdoga.szakdoga.app.repository.ProductRepository;
import com.szakdoga.szakdoga.app.repository.AppUserRepository;
import com.szakdoga.szakdoga.app.repository.entity.Component;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import com.szakdoga.szakdoga.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.stream.Collectors;


@EnableScheduling
@SpringBootApplication
public class SzakdogaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SzakdogaApplication.class, args);

    }

}
