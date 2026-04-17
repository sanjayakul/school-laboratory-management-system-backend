<<<<<<< HEAD
package com.laboratory.management.system.controller;

import com.laboratory.management.system.exceptions.DataNotFoundException;
import com.laboratory.management.system.model.dto.CreateUserRequestDto;
import com.laboratory.management.system.model.dto.LoginRequestDto;
import com.laboratory.management.system.model.dto.LoginResponseDto;
import com.laboratory.management.system.model.dto.UserDto;
import com.laboratory.management.system.service.UserService;
import com.laboratory.management.system.service.criteria.UserCriteria;
import com.laboratory.management.system.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "UserController", description = "The User API Services")
public class UserController extends Response {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    public ResponseEntity<Response> createUser(@RequestBody CreateUserRequestDto request) {
        logger.info("UserController -> createUser() => started");
        try {
            return ResponseEntity.ok(response(userService.createUser(request), "user created successfully"));
        } catch (IllegalArgumentException e) {
            logger.error("UserController -> createUser() => validation failed : {}", e.getMessage());
            return ResponseEntity.badRequest().body(error(e.getMessage(), "user creation failed!"));
        } catch (DataIntegrityViolationException e) {
            logger.error("UserController -> createUser() => constraint violation exception : {}", e.getMostSpecificCause().getMessage());
            return ResponseEntity.badRequest().body(error(e.getMostSpecificCause().getMessage(), "constraint violation exception!"));
        } catch (Exception e) {
            logger.error("UserController -> createUser() => user creation failed : {}", e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @GetMapping(value = "/users/filters", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Filter System Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    public Page<UserDto> findAppraisalsByFilters(@RequestParam(value = "status", required = false) String status,
                                                 @RequestParam(value = "userName", required = false) String userName,
                                                 @RequestParam(value = "fullName", required = false) String fullName, @RequestParam(value = "email", required = false) String email,
                                                 @RequestParam(value = "role", required = false) String role,
                                                 @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size

    ) throws DataNotFoundException {
        var criteria = UserCriteria
                .builder()
                .status(status)
                .userName(userName)
                .fullName(fullName)
                .role(role)
                .email(email)
                .build();

        Pageable pageable = createPageRequestUsing(page, size);
        return userService.findUserByFilters(criteria, pageable);
    }

    private Pageable createPageRequestUsing(int page, int size) {

        List<Sort.Order> orders = new ArrayList<>();

        orders.add(new Sort.Order(Sort.Direction.DESC, "modifiedDate"));

        return PageRequest.of(page, size, Sort.by(orders));
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "User Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login Successful",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        logger.info("UserController -> login() => started");
        try {
            LoginResponseDto response = userService.login(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("UserController -> login() => invalid credentials : {}", e.getMessage());
            return ResponseEntity.status(401).body(new LoginResponseDto(null, null, null, null, null, "Invalid credentials"));
        } catch (Exception e) {
            logger.error("UserController -> login() => error : {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new LoginResponseDto(null, null, null, null, null, "Login failed: " + e.getMessage()));
        }
    }
}
=======
package com.laboratory.management.system.controller;

import com.laboratory.management.system.exceptions.DataNotFoundException;
import com.laboratory.management.system.model.dto.CreateUserRequestDto;
import com.laboratory.management.system.model.dto.LoginRequestDto;
import com.laboratory.management.system.model.dto.LoginResponseDto;
import com.laboratory.management.system.model.dto.UserDto;
import com.laboratory.management.system.service.UserService;
import com.laboratory.management.system.service.criteria.UserCriteria;
import com.laboratory.management.system.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "UserController", description = "The User API Services")
public class UserController extends Response {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    public ResponseEntity<Response> createUser(@RequestBody CreateUserRequestDto request) {
        logger.info("UserController -> createUser() => started");
        try {
            return ResponseEntity.ok(response(userService.createUser(request), "user created successfully"));
        } catch (IllegalArgumentException e) {
            logger.error("UserController -> createUser() => validation failed : {}", e.getMessage());
            return ResponseEntity.badRequest().body(error(e.getMessage(), "user creation failed!"));
        } catch (DataIntegrityViolationException e) {
            logger.error("UserController -> createUser() => constraint violation exception : {}", e.getMostSpecificCause().getMessage());
            return ResponseEntity.badRequest().body(error(e.getMostSpecificCause().getMessage(), "constraint violation exception!"));
        } catch (Exception e) {
            logger.error("UserController -> createUser() => user creation failed : {}", e.getMessage());
            return ResponseEntity.internalServerError().body(error(e.getMessage()));
        }
    }

    @GetMapping(value = "/users/filters", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Filter System Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    public Page<UserDto> findAppraisalsByFilters(@RequestParam(value = "status", required = false) String status,
                                                 @RequestParam(value = "userName", required = false) String userName,
                                                 @RequestParam(value = "fullName", required = false) String fullName, @RequestParam(value = "email", required = false) String email,
                                                 @RequestParam(value = "role", required = false) String role,
                                                 @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size

    ) throws DataNotFoundException {
        var criteria = UserCriteria
                .builder()
                .status(status)
                .userName(userName)
                .fullName(fullName)
                .role(role)
                .email(email)
                .build();

        Pageable pageable = createPageRequestUsing(page, size);
        return userService.findUserByFilters(criteria, pageable);
    }

    private Pageable createPageRequestUsing(int page, int size) {

        List<Sort.Order> orders = new ArrayList<>();

        orders.add(new Sort.Order(Sort.Direction.DESC, "modifiedDate"));

        return PageRequest.of(page, size, Sort.by(orders));
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "User Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login Successful",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        logger.info("UserController -> login() => started");
        try {
            LoginResponseDto response = userService.login(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("UserController -> login() => invalid credentials : {}", e.getMessage());
            return ResponseEntity.status(401).body(new LoginResponseDto(null, null, null, null, null, "Invalid credentials"));
        } catch (Exception e) {
            logger.error("UserController -> login() => error : {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new LoginResponseDto(null, null, null, null, null, "Login failed: " + e.getMessage()));
        }
    }
}
>>>>>>> e4e22ab (Initial commit)
