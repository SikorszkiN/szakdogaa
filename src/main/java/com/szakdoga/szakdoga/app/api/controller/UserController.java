package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.AppUserDto;
import com.szakdoga.szakdoga.app.mapper.UserMapper;
import com.szakdoga.szakdoga.app.repository.entity.AppUser;
import com.szakdoga.szakdoga.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/all")
    public List<AppUser> getAllUsers(){
        return userService.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUserDto appUserDto){
        return ResponseEntity.ok(userService.saveUser(appUserDto));
    }

    @PostMapping("{userId}/products/{productId}")
    public void saveProductComponent(@PathVariable Long userId, @PathVariable Long productId){
        userService.saveUserProduct(userId, productId);
    }

    @GetMapping("/order/{userId}")
    public String orders(@PathVariable Long userId){
        return "" + userService.orderedProducts(userId);
    }
}
