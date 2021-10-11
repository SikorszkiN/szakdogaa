package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.WebshopDto;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import com.szakdoga.szakdoga.app.service.WebshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webshop")
public class WebshopController {

    private final WebshopService webshopService;

    @PostMapping("/save")
    public ResponseEntity<WebshopDto> save(@RequestBody WebshopDto webshopDto){
        return ResponseEntity.ok(webshopService.save(webshopDto));
    }

}
