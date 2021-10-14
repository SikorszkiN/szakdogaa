package com.szakdoga.szakdoga.app.mapper;

import com.szakdoga.szakdoga.app.dto.AppUserDto;
import com.szakdoga.szakdoga.app.repository.entity.AppUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public AppUserDto userToUserDto(AppUser appUser){

        AppUserDto appUserDto = new AppUserDto();

        appUserDto.setFirstName(appUser.getFirstName());
        appUserDto.setLastName(appUser.getLastName());
        appUserDto.setEmail(appUser.getEmail());

        return appUserDto;
    }

    public AppUser userDtoToUser(AppUserDto appUserDto){

        AppUser appUser = new AppUser();

        appUser.setFirstName(appUserDto.getFirstName());
        appUser.setLastName(appUserDto.getLastName());
        appUser.setEmail(appUserDto.getEmail());

        return appUser;
    }

}
