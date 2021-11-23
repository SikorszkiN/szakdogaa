package com.szakdoga.szakdoga.app.repository;

import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WebshopProductRepository extends JpaRepository<WebshopProduct, Long> {

    List<WebshopProduct> findAllByName(String name);

    Page<WebshopProduct> findAll(Pageable pageable);
}
