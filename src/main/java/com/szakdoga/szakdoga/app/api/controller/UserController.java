package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.UserDto;
import com.szakdoga.szakdoga.app.mapper.UserMapper;
import com.szakdoga.szakdoga.app.repository.entity.User;
import com.szakdoga.szakdoga.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.saveUser(userMapper.userDtoToUser(userDto)));
    }

    @Transactional
    @PostMapping("{userId}/products/{productId}")
    public void saveProductComponent(@PathVariable Long userId, @PathVariable Long productId){
        userService.saveUserProduct(userId, productId);
    }
}
