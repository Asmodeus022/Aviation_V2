package com.barcode.aviation.tool.inventory.dto;

import com.barcode.aviation.tool.inventory.entities.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityDto {
    private Long id;
    private String username;
    private String fullname;
    private String password;
    private UserRole role;
}
