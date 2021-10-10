package com.szakdoga.szakdoga.app.repository;

import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebshopRepository extends JpaRepository<Webshop,Long> {

    Optional<Webshop> findByName(String name);

}
