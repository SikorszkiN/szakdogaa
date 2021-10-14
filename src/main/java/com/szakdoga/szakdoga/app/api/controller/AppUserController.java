package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.AppUserDto;
import com.szakdoga.szakdoga.app.mapper.UserMapper;
import com.szakdoga.szakdoga.app.repository.entity.AppUser;
import com.szakdoga.szakdoga.app.service.AppUserService;
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
@RequestMapping("/api/user")
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/all")
    public List<AppUser> getAllUsers(){
        return appUserService.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUserDto appUserDto){
        return ResponseEntity.ok(appUserService.saveUser(appUserDto));
    }

    @PostMapping("{userId}/products/{productId}")
    public void saveUserProduct(@PathVariable Long userId, @PathVariable Long productId){
        appUserService.saveUserProduct(userId, productId);
    }

    @GetMapping("/order/{userId}")
    public String orders(@PathVariable Long userId){
        return "" + appUserService.orderedProducts(userId);
    }

    @DeleteMapping("/delete/{appUserId}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable @Valid Long appUserId){
        appUserService.deleteUser(appUserId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{appUserId}")
    public ResponseEntity<Map<String, Boolean>>  sendCalculation(@PathVariable @Valid Long appUserId){
        appUserService.sendOrderCalculation(appUserId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Email sent", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}
