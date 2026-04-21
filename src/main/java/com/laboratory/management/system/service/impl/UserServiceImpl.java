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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.laboratory.management.system.util.Enums.ACTIVE;
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final org.modelmapper.ModelMapper mapper;
    private final JavaMailSender mailSender;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, org.modelmapper.ModelMapper mapper, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.mailSender = mailSender;
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

            user.setEmail(normalizedEmail);
            user.setUsername(normalizedUsername);
            user.setFullName(request.getFullName() != null ? request.getFullName().trim() : null);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole().trim().toUpperCase());
            user.setDepartment(request.getDepartment() != null ? request.getDepartment().trim() : null);
            user.setStatus(request.getStatus() == null ? ACTIVE.value() : request.getStatus().trim().toUpperCase());

            User savedUser = userRepository.save(user);

            // ✅ SEND EMAIL (same pattern as your example)
            sendUserCreationEmail(savedUser, request.getPassword());

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

    private void sendUserCreationEmail(User user, String rawPassword) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("Your Account Has Been Created");

            // Load HTML template
            String htmlContent = loadUserCreationTemplate();

            // Replace placeholders
            htmlContent = htmlContent.replace("{{USER_NAME}}", user.getFullName());
            htmlContent = htmlContent.replace("{{USERNAME}}", user.getUsername());
            htmlContent = htmlContent.replace("{{EMAIL}}", user.getEmail());
            htmlContent = htmlContent.replace("{{PASSWORD}}", rawPassword);
            htmlContent = htmlContent.replace("{{ROLE}}", user.getRole());
            htmlContent = htmlContent.replace("{{DEPARTMENT}}", user.getDepartment());

            helper.setText(htmlContent, true);

            // Inline logo (same as your example)
            ClassPathResource logoResource = new ClassPathResource("assets/logo.png");
            helper.addInline("logo", logoResource);

            mailSender.send(mimeMessage);

            log.info("User creation email sent successfully to: {}", user.getEmail());

        } catch (Exception e) {
            log.error("Failed to send user creation email to: {}. Error: {}", user.getEmail(), e.getMessage());

            // Optional: fallback logging (like your example)
            log.warn("==========================================================");
            log.warn("EMAIL FAILED - USER CREATED SUCCESSFULLY");
            log.warn("User: {}", user.getUsername());
            log.warn("Password: {}", rawPassword);
            log.warn("==========================================================");
        }
    }

    private String loadUserCreationTemplate() throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/user-creation-email.html");

        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }
}
