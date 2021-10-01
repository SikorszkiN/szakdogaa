package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.WebshopDto;
import com.szakdoga.szakdoga.app.mapper.WebshopMapper;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import com.szakdoga.szakdoga.app.service.WebshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webshop")
public class WebshopController {

    private final WebshopService webshopService;
    private final WebshopMapper webshopMapper;

    @PostMapping("/save")
    public ResponseEntity<Webshop> saveWebshop(@RequestBody WebshopDto webshopDto){
        return ResponseEntity.ok(webshopService.saveWebshop(webshopDto));
    }
}
