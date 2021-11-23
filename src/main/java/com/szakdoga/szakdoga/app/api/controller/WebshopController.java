package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.UpdateWebshopData;
import com.szakdoga.szakdoga.app.dto.WebshopDto;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import com.szakdoga.szakdoga.app.service.WebshopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webshops")
public class WebshopController {

    private final WebshopService webshopService;

    @GetMapping
    public ResponseEntity<List<Webshop>> findAll(){
        return ResponseEntity.ok(webshopService.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<WebshopDto> save(@RequestBody WebshopDto webshopDto){
        log.info("New Webshop save to database");
        return ResponseEntity.ok(webshopService.save(webshopDto));
    }

    @DeleteMapping("/{webshopId}")
    public ResponseEntity<Map<String, Boolean>> deleteWebshop(@PathVariable @Valid Long webshopId){
        log.info("Delete Webshop from the database!");
        webshopService.deleteWebshop(webshopId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{webshopId}")
    public ResponseEntity<Map<String, Boolean>> updateWebshop(@PathVariable @Valid Long webshopId, @RequestBody @Valid UpdateWebshopData updateWebshopData){
        log.info("Delete Webshop");
        webshopService.updateWebshop(webshopId, updateWebshopData);
        Map<String, Boolean> response = new HashMap<>();
        response.put("updated", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{webshopId}")
    public ResponseEntity<Webshop> findProductById(@PathVariable Long webshopId){
        return ResponseEntity.ok(webshopService.findById(webshopId));
    }

}
