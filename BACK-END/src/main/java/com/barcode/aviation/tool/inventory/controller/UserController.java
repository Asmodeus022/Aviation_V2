
package com.barcode.aviation.tool.inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barcode.aviation.tool.inventory.dto.UserDto;
import com.barcode.aviation.tool.inventory.service.UserService;

import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    //Build Add User Rest API
    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto){
        UserDto savedUser = userService.addUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    //Build Get User REST API
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId){
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    //Build Gett All User REST API
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //Build Update User REST API
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable ("id") Long employeeId,
                                                      @RequestBody UserDto updatedUser) {
        UserDto userDto = userService.updateUser(employeeId, updatedUser);
        return ResponseEntity.ok(userDto);
    }
    
    //Build Delete User REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted Successfully!.");
    }

}
