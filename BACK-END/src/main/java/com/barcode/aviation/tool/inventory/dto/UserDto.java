package com.barcode.aviation.tool.inventory.dto;

import com.barcode.aviation.tool.inventory.entities.UserType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String email;
    private String fullname;
    private UserType userType;
}
