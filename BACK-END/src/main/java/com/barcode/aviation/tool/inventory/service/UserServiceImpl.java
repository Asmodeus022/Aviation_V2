package com.barcode.aviation.tool.inventory.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.barcode.aviation.tool.inventory.dto.UserDto;
import com.barcode.aviation.tool.inventory.entities.User;
import com.barcode.aviation.tool.inventory.repository.UserRepository;
import com.barcode.aviation.tool.inventory.exception.ResourceNotFoundException;
import com.barcode.aviation.tool.inventory.mapper.UserMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User is note exists with given id: "+ userId));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user)
        ->UserMapper.mapToUserDto(user))
        .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long userId, UserDto updatedUser) {
        userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("User is not exists with given id:" + userId)
        );
    
        User user2 = UserMapper.mapToUser(updatedUser);
    
        User updatedUserObj = userRepository.save(user2);
    
        return UserMapper.mapToUserDto(updatedUserObj);
    }
    

    @Override
    public void deleteUser(Long userId) {
        @SuppressWarnings("unused") 
        User user = userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("User is not exists with given id:" + userId)
        );
        userRepository.deleteById(userId);
    }

}
