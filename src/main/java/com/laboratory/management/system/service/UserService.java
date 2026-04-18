package com.laboratory.management.system.service;

import com.laboratory.management.system.model.dto.CreateUserRequestDto;
import com.laboratory.management.system.model.dto.LoginRequestDto;
import com.laboratory.management.system.model.dto.LoginResponseDto;
import com.laboratory.management.system.model.dto.UserDto;
import com.laboratory.management.system.service.criteria.UserCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    LoginResponseDto login(LoginRequestDto request);

    UserDto createUser(CreateUserRequestDto request);

    Page<UserDto> findUserByFilters(UserCriteria criteria, Pageable pageable);
}
