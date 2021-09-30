package com.szakdoga.szakdoga.app.repository;

import com.szakdoga.szakdoga.app.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();

    @Query("select p from Product p where p.name like :name")
    List<Product> findByName(@Param("name") String name);

   /* @Query("select p from Product p where p.name like :name order by p.price")
    List<Product> findCheapProduct(String name);*/


}
