package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.ComponentDto;
import com.szakdoga.szakdoga.app.mapper.ComponentMapper;
import com.szakdoga.szakdoga.app.repository.entity.Component;
import com.szakdoga.szakdoga.app.service.ComponentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/component")
public class ComponentController {

    private final ComponentService componentService;
    private final ComponentMapper componentMapper;

    @PostMapping("/save")
    public ResponseEntity<Component> saveComponent(@RequestBody ComponentDto componentDto){ //response entity tudja kezelni milyen http status kóddal tér vissza
        return ResponseEntity.ok(componentService.saveComponent(componentMapper.componentDtoToComponent(componentDto)));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Component>> findAllComponent(){
        return ResponseEntity.ok(componentService.findAll());
    }

    @PostMapping("/{componentId}/webshop/{webshopId}")
    public String saveWebshopToComponent(@PathVariable Long componentId, @PathVariable Long webshopId){
        componentService.addWebshopToComponent(componentId, webshopId);
        return "Talán sikerült  egy webshopot a komponenshez";
    }

    @Transactional
    @PostMapping("/{componentId}/addwebshops")
    public String saveWebshopToComponent(@PathVariable Long componentId, @RequestBody String name){
        componentService.WebshopsToComponent(componentId, name);
        return "Talán sikerült elmenteni név alapján";
    }

    /*@GetMapping("/cheap/{componentId}")
    public int cheapestWebshop(@PathVariable Long componentId){
       return componentService.getCheapestWebshopPrice(componentId);
    }*/

    /*@GetMapping("/cheap/name/{componentId}")
    public List<Webshop> cheapestWebshopName(@PathVariable Long componentId){
        return componentService.getCheapestWebshopData(componentId);
    }*/

}
