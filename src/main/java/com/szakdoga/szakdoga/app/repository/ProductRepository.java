package com.szakdoga.szakdoga.app.repository;

import com.szakdoga.szakdoga.app.dto.ComponentPriceCalculate;
import com.szakdoga.szakdoga.app.repository.entity.Component;
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




/*    @Query(value = "select wp.price from webshop_product wp join component_webshopProduct cwp ON cwp.webshop_product_id = wp.id
           where c.name = 'ASUS Intel Prime H510M'",
           nativeQuery = true)*/
   /*@Query("select cwp.id, wp.price, c.name from WebshopProduct wp join WebshopProduct.webshop cwp ON cwp.id = wp.id join Component c ON c.id = cwp.id where c.name = 'Asus Intel Prime H510M'")*/



    @Query("select cwp.id, wp.price from WebshopProduct wp join wp.components cwp ON cwp.id = wp.id join Component c ON c.id = cwp.id  where c.name='Kingston A400 SSD meghajtó, 240GB'")
   List<String> valami();


}
