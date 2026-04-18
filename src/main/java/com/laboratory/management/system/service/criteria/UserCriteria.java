package com.laboratory.management.system.service.criteria;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserCriteria {
    private String status;
    private String userName;
    private String fullName;
    private String role;
    private String email;

}