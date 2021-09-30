package com.szakdoga.szakdoga.app.mapper;

import com.szakdoga.szakdoga.app.dto.UserDto;
import com.szakdoga.szakdoga.app.repository.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class UserMapper {

    private ModelMapper modelMapper;

    public UserDto userToUserDto(User user){

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPasssword(user.getPassword());

        return userDto;
       // return modelMapper.map(user, UserDto.class);
    }

    public User userDtoToUser(UserDto userDto){

        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPasssword());

        return user;
      //  return modelMapper.map(userDto, User.class);
    }

}
