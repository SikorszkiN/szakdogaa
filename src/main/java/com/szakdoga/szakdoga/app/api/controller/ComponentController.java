package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.ComponentDto;
import com.szakdoga.szakdoga.app.repository.entity.Component;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import com.szakdoga.szakdoga.app.service.ComponentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/component")
public class ComponentController {

    private final ComponentService componentService;

    @PostMapping("/save")
    public ResponseEntity<Component> saveComponent(@RequestBody ComponentDto componentDto){ //response entity tudja kezelni milyen http status kóddal tér vissza
        return ResponseEntity.ok(componentService.saveComponent(componentDto));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Component>> findAllComponent(){
        return ResponseEntity.ok(componentService.findAll());
    }

    @PostMapping("/{componentId}/webshopproduct/{webshopproductId}")
    public ResponseEntity<Map<String, Boolean>> saveWebshopToComponent(@PathVariable Long componentId, @PathVariable Long webshopproductId){
        componentService.addWebshopProductToComponent(componentId, webshopproductId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Hozzáadás sikeres:", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{componentId}/addwebshops")
    public String saveWebshopToComponent(@PathVariable Long componentId, @RequestBody String name){
        componentService.WebshopsToComponent(componentId, name);
        return "Talán sikerült elmenteni név alapján";
    }

    @GetMapping("/cheap/name/{componentId}")
    public WebshopProduct cheapestWebshopName(@PathVariable Long componentId){
        return componentService.getCheapestWebshopData(componentId);
    }

    @DeleteMapping("/delete/{componentId}")
    public ResponseEntity<Map<String, Boolean>> deleteComponent(@PathVariable @Valid Long componentId){
        componentService.deleteComponent(componentId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{componentId}")
    public ResponseEntity<Map<String, Boolean>> updateProduct(@PathVariable @Valid Long componentId, @RequestBody String name){
        componentService.updateComponent(componentId, name);
        Map<String, Boolean> response = new HashMap<>();
        response.put("updated", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
