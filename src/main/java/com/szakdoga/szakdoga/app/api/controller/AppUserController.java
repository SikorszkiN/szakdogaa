package com.szakdoga.szakdoga.app.api.controller;

import com.szakdoga.szakdoga.app.dto.AppUserDto;
import com.szakdoga.szakdoga.app.exception.ApiRequestException;
import com.szakdoga.szakdoga.app.repository.entity.AppUser;
import com.szakdoga.szakdoga.app.repository.entity.Blacklist;
import com.szakdoga.szakdoga.app.repository.entity.UserRole;
import com.szakdoga.szakdoga.app.service.AppUserService;
import com.szakdoga.szakdoga.app.service.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appuser")
public class AppUserController {

    private final AppUserService appUserService;

    private final BlacklistService blacklistService;

    @GetMapping("/all")
    public List<AppUser> getAllUsers(){
        return appUserService.findAll();
    }

/*    @PostMapping("/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody @Valid AppUserDto appUserDto){
        return ResponseEntity.ok(appUserService.saveUser(appUserDto));
    }*/

    @PostMapping("{userId}/products/{productId}")
    public void saveUserProduct(@PathVariable @Valid Long userId, @PathVariable @Valid Long productId){
        appUserService.saveUserProduct(userId, productId);
    }

    @DeleteMapping("/delete/{appUserId}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable @Valid Long appUserId){
        appUserService.deleteUser(appUserId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    /*    @GetMapping("/email/{appUserId}")
        public ResponseEntity<Map<String, Boolean>> sendCalculation(@PathVariable @Valid Long appUserId){
            appUserService.sendOrderCalculation(appUserId);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Email sent", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }*/

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

        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        return blacklistService.addTokenToBlacklist(token);
    }


}
