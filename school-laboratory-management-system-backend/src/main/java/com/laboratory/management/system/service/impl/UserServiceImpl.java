package com.laboratory.management.system.service.impl;

import com.laboratory.management.system.model.User;
import com.laboratory.management.system.model.dto.CreateUserRequestDto;
import com.laboratory.management.system.model.dto.LoginRequestDto;
import com.laboratory.management.system.model.dto.LoginResponseDto;
import com.laboratory.management.system.model.dto.UserDto;
import com.laboratory.management.system.repository.UserRepository;
import com.laboratory.management.system.service.UserService;
import com.laboratory.management.system.service.criteria.UserCriteria;
import com.laboratory.management.system.service.specification.UserSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.laboratory.management.system.util.Enums.ACTIVE;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final org.modelmapper.ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, org.modelmapper.ModelMapper mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public UserDto createUser(CreateUserRequestDto request) {
        logger.info("UserServiceImpl => createUser() => started!");
        try {
            String normalizedEmail = request.getEmail().trim().toLowerCase();
            String normalizedUsername = request.getUsername().trim();

            if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
                throw new IllegalArgumentException("Email is already in use");
            }

            if (userRepository.existsByUsernameIgnoreCase(normalizedUsername)) {
                throw new IllegalArgumentException("Username is already in use");
            }

            User user = mapper.map(request, User.class);

            user.setEmail(request.getEmail().trim().toLowerCase());
            user.setUsername(request.getUsername().trim());
            user.setFullName(request.getFullName() != null ? request.getFullName().trim() : null);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole().trim().toUpperCase());
            user.setDepartment(request.getDepartment() != null ? request.getDepartment().trim() : null);
            user.setStatus(request.getStatus() == null ? ACTIVE.value() : request.getStatus().trim().toUpperCase());


            User savedUser = userRepository.save(user);

            logger.info("UserServiceImpl => createUser() => Finished!");

            return mapper.map(savedUser, UserDto.class);

        } catch (Exception ex) {
            logger.error("Error in create User: ", ex);
            throw new RuntimeException("Failed to create User", ex);
        }
    }

    @Override
    public Page<UserDto> findUserByFilters(UserCriteria criteria, Pageable pageable) {
        logger.info("UserServiceImpl -> findUserByFilters => Started!");

        Specification specification = Specification
                .where(UserSpecification.matchStatus(criteria.getStatus()))
                .and(UserSpecification.matchUserName(criteria.getUserName()))
                .and(UserSpecification.matchFullName(criteria.getFullName()))
                .and(UserSpecification.matchRole(criteria.getRole()))
                .and(UserSpecification.matchEmail(criteria.getEmail()));

        Page<User> users = userRepository.findAll(specification, pageable);

        Page<UserDto> userDtos = users.map(summary -> mapper.map(summary, UserDto.class));

        return userDtos;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        logger.info("UserServiceImpl -> login() => started!");
        String usernameOrEmail = request.getUsernameOrEmail();
        String password = request.getPassword();
        User user = null;
        if (usernameOrEmail.contains("@")) {
            user = userRepository.findAll().stream()
                    .filter(u -> u.getEmail().equalsIgnoreCase(usernameOrEmail))
                    .findFirst().orElse(null);
        } else {
            user = userRepository.findAll().stream()
                    .filter(u -> u.getUsername().equalsIgnoreCase(usernameOrEmail))
                    .findFirst().orElse(null);
        }
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return new LoginResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                "Login successful"
        );
    }
}
