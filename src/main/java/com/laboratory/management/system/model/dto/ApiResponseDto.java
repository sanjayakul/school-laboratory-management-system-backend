package com.laboratory.management.system.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ApiResponseDto {
    private Object data;
    private String message;
    private Date timestamp = new Date();
}
