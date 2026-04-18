
package com.laboratory.management.system.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private Long userId;
    private String username;
    private String email;
    private String role;
    private String status;
    private String message;
}
