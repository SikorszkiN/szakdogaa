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

   /* @Query("select wp.*, w.delivery_price from webshop_product wp\n" +
            "JOIN component_webshop_product cwp ON cwp.webshop_product_id = wp.id\n" +
            "JOIN component c ON c.id = cwp.component_id\n" +
            "JOIN webshop w ON w.id = wp.webshop_id\n" +
            "WHERE c.\\"name\\" =  'Asus Intel Prime H510M'")
    List<Object> findValami();*/

}
