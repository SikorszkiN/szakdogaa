package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.UpdateUserData;
import com.szakdoga.szakdoga.app.exception.ApiRequestException;
import com.szakdoga.szakdoga.app.repository.entity.AppUser;
import com.szakdoga.szakdoga.app.repository.entity.Blacklist;
import com.szakdoga.szakdoga.app.repository.entity.UserRole;
import com.szakdoga.szakdoga.app.service.AppUserService;
import com.szakdoga.szakdoga.app.service.BlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appusers")
public class AppUserController {

    private final AppUserService appUserService;

    private final BlacklistService blacklistService;

    @GetMapping
    public List<AppUser> getAllUsers(){
        return appUserService.findAll();
    }

    @GetMapping("/findbyid/{appUserId}")
    public ResponseEntity<AppUser> findById(@PathVariable @Valid Long appUserId){
        return ResponseEntity.ok(appUserService.findById(appUserId));
    }

    @PostMapping("{appuserid}/products/{productId}")
    public void saveUserProduct(@PathVariable @Valid Long appuserid, @PathVariable @Valid Long productId){
        log.info("Saving product to the user!");
        appUserService.saveUserProduct(appuserid, productId);
    }

    @DeleteMapping("/{appUserId}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable @Valid Long appUserId){
        log.info("Delete user from the database!");
        appUserService.deleteUser(appUserId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/changerole/{appUserId}")
    public ResponseEntity<Map<String, Boolean>> changeRole(@PathVariable @Valid Long appUserId, @RequestBody String role){
        UserRole userRole;
        if (role.equals("ADMIN")){
            userRole = UserRole.ADMIN;
        } else if (role.equals("USER")){
            userRole = UserRole.USER;
        } else {
            throw new ApiRequestException("Hiba, kérlek válassz, a USER és az ADMIN role-ok közül");
        }
        appUserService.changeRole(appUserId, userRole);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Role changed", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public Blacklist logout(HttpServletRequest httpServletRequest) {
        log.info("User logged out!");

        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        return blacklistService.addTokenToBlacklist(token);
    }

    @PutMapping("/{appuserid}")
    public ResponseEntity<Map<String, Boolean>> updateUserData(@PathVariable @Valid Long appuserid, @RequestBody UpdateUserData updateUserData){
        log.info("User Data changed!");
        appUserService.updateUser(appuserid, updateUserData);
        Map<String, Boolean> response = new HashMap<>();
        response.put("User data changed", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
