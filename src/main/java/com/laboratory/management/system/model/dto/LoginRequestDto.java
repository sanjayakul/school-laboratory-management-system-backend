package com.laboratory.management.system.model.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String usernameOrEmail;
    private String password;
}
