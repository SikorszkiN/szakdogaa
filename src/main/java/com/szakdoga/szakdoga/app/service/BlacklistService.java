package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.repository.BlacklistRepository;
import com.szakdoga.szakdoga.app.repository.entity.Blacklist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlacklistService {

   private final BlacklistRepository blacklistRepository;

   public Blacklist addTokenToBlacklist(String token){

       Blacklist blacklist = new Blacklist(token);
       return blacklistRepository.save(blacklist);
   }

}
