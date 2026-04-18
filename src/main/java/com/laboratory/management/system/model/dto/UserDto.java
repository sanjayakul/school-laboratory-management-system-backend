package com.laboratory.management.system.model.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String fullName;
    private String email;
    private String username;
    private String role;
    private String department;
    private String status;
}
