package com.example.webnovelservice.user.controller;

import com.example.webnovelservice.commons.response.ResponseEntityBuilder;
import com.example.webnovelservice.commons.response.exception.ResourceNotFoundException;
import com.example.webnovelservice.user.domain.repository.UserRepository;
import com.example.webnovelservice.user.domain.entity.User;
import com.example.webnovelservice.commons.annotation.CurrentUser;
import com.example.webnovelservice.commons.security.UserPrincipal;
import com.example.webnovelservice.user.domain.service.UserService;
import com.example.webnovelservice.user.dto.response.UserDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "user info", description = "get information of current user")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        UserDto userDto = userService.checkUserExists(userPrincipal.getId());
        return ResponseEntityBuilder.build(HttpStatus.OK, "200 000", userDto);
    }
}
