package com.barcode.aviation.tool.inventory.mapper;

import com.barcode.aviation.tool.inventory.dto.UserEntityDto;
import com.barcode.aviation.tool.inventory.entities.UserEntity;

public class UserEntityMapper {
    public static UserEntityDto maptoUserDto(UserEntity userEntity){
        return new UserEntityDto(
            userEntity.getId(),
            userEntity.getUsername(),
            userEntity.getFullname(),
            userEntity.getPassword(),
            userEntity.getRole()
        );
    }   
    
    public static UserEntity maptoUser(UserEntityDto userDto){
        // return new UserEntity(
        //     userDto.getId(),
        //     userDto.getUsername(),
        //     userDto.getFullname(),
        //     userDto.getPassword(),
        //     userDto.getRole()
        // );
        return null;
    }
}
