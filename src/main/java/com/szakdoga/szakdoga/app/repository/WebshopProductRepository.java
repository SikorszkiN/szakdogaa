package com.szakdoga.szakdoga.app.repository;

import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebshopProductRepository extends JpaRepository<WebshopProduct, Long> {

    List<WebshopProduct> findAllByName(String name);
}
