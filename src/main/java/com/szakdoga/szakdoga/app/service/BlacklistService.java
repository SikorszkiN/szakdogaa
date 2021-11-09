package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.repository.BlacklistRepository;
import com.szakdoga.szakdoga.app.repository.entity.Blacklist;
import com.szakdoga.szakdoga.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlacklistService {

   private final BlacklistRepository blacklistRepository;

   private final JwtTokenUtil jwtTokenUtil;

   public Blacklist addTokenToBlacklist(String token){

       Blacklist blacklist = new Blacklist(token);
       return blacklistRepository.save(blacklist);
   }

   @Scheduled(cron = "0 0 0 * * *")
   public void deleteBlackListToken(){
       List<Blacklist> blacklistTokens = blacklistRepository.findAll();
       for(Blacklist blacklistToken : blacklistTokens) {
           if (!jwtTokenUtil.validate(blacklistToken.getToken())){
               blacklistRepository.delete(blacklistToken);
               log.info("JWT token deleted!");
           }
       }
    }

}
