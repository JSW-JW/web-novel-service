package com.example.webnovelservice.user.controller;

import com.example.webnovelservice.commons.response.exception.ResourceNotFoundException;
import com.example.webnovelservice.user.domain.repository.UserRepository;
import com.example.webnovelservice.user.domain.entity.User;
import com.example.webnovelservice.commons.annotation.CurrentUser;
import com.example.webnovelservice.commons.security.UserPrincipal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(summary = "user info", description = "get information of current user")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
