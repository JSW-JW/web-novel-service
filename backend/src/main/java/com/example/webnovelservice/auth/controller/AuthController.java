package com.example.webnovelservice.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webnovelservice.auth.domain.service.AuthService;
import com.example.webnovelservice.auth.dto.response.AuthDto;
import com.example.webnovelservice.auth.exception.UserNotFoundException;
import com.example.webnovelservice.commons.response.ResponseEntityBuilder;
import com.example.webnovelservice.user.dto.request.LoginRequest;
import com.example.webnovelservice.user.dto.request.SignUpRequest;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // check if user exists and password match
        authService.checkIfUSerExists(loginRequest.getEmail());

        String token = authService.authenticateAndReturnAccessToken(loginRequest);
        return ResponseEntityBuilder.build(HttpStatus.OK, "200 000", new AuthDto(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        String token = authService.registerUser(signUpRequest);
        return ResponseEntityBuilder.build(HttpStatus.OK, "200 000", new AuthDto(token));
    }

}
