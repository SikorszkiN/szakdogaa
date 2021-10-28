package com.szakdoga.szakdoga.app.repository;

import com.szakdoga.szakdoga.app.repository.entity.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {

    Blacklist findByToken(String token);
}
