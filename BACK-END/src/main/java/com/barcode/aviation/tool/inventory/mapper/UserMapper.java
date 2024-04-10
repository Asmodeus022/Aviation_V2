package com.barcode.aviation.tool.inventory.mapper;

import com.barcode.aviation.tool.inventory.dto.UserDto;
import com.barcode.aviation.tool.inventory.entities.User;

public class UserMapper {
    
    public static UserDto mapToUserDto(User user) {
        return new UserDto(
            user.getUserId(),
            user.getEmail(),
            user.getFullname(),
            user.getUserType()
        );
    }

    public static User mapToUser(UserDto userDto) {
        return new User(
            userDto.getUserId(),
            userDto.getEmail(),
            userDto.getFullname(),
            userDto.getUserType()
        );
    }
}
