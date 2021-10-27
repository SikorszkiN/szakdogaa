package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.UpdateWebshopData;
import com.szakdoga.szakdoga.app.dto.UpdateWebshopProduct;
import com.szakdoga.szakdoga.app.dto.WebshopDto;
import com.szakdoga.szakdoga.app.service.WebshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webshop")
public class WebshopController {

    private final WebshopService webshopService;

    @PostMapping("/save")
    public ResponseEntity<WebshopDto> save(@RequestBody WebshopDto webshopDto){
        return ResponseEntity.ok(webshopService.save(webshopDto));
    }

    @DeleteMapping("/delete/{webshopId}")
    public ResponseEntity<Map<String, Boolean>> deleteWebshop(@PathVariable @Valid Long webshopId){
        webshopService.deleteWebshop(webshopId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{webshopId}")
    public ResponseEntity<Map<String, Boolean>> updateWebshop(@PathVariable @Valid Long webshopId, @RequestBody @Valid UpdateWebshopData updateWebshopData){
        webshopService.updateWebshop(webshopId, updateWebshopData);
        Map<String, Boolean> response = new HashMap<>();
        response.put("updated", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}
