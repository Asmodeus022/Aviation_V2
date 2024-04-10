package com.barcode.aviation.tool.inventory.service;

import java.util.List;

import com.barcode.aviation.tool.inventory.dto.UserDto;

public interface UserService {
    UserDto addUser(UserDto userDto);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(Long userId, UserDto updatedUser);

    void deleteUser(Long userId);

}
