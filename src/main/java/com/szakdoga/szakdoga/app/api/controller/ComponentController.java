package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.ComponentDto;
import com.szakdoga.szakdoga.app.repository.entity.Component;
import com.szakdoga.szakdoga.app.service.ComponentService;
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
@RequestMapping("/api/components")
public class ComponentController {

    private final ComponentService componentService;

    @PostMapping("/add")
    public ResponseEntity<Component> saveComponent(@RequestBody ComponentDto componentDto){ //response entity tudja kezelni milyen http status kóddal tér vissza
        log.info("Saving new Component to the database!");
        return ResponseEntity.ok(componentService.saveComponent(componentDto));
    }

    @GetMapping()
    public ResponseEntity<List<Component>> findAllComponent(){
        return ResponseEntity.ok(componentService.findAll());
    }

    @GetMapping("/{componentId}")
    public ResponseEntity<Component> findProductById(@PathVariable Long componentId){
        return ResponseEntity.ok(componentService.findById(componentId));
    }

    @PostMapping("/{componentId}/webshopproducts/{webshopproductId}")
    public ResponseEntity<Map<String, Boolean>> saveWebshopToComponent(@PathVariable Long componentId, @PathVariable Long webshopproductId){
        log.info("Saving Webshop Product to Component");
        componentService.addWebshopProductToComponent(componentId, webshopproductId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Hozzáadás sikeres:", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{componentId}")
    public ResponseEntity<Map<String, Boolean>> deleteComponent(@PathVariable @Valid Long componentId){
        log.info("Delete Component!");
        componentService.deleteComponent(componentId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{componentId}")
    public ResponseEntity<Map<String, Boolean>> updateComponent(@PathVariable @Valid Long componentId, @RequestBody String name){
        log.info("Modify Component");
        componentService.updateComponent(componentId, name);
        Map<String, Boolean> response = new HashMap<>();
        response.put("updated", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
