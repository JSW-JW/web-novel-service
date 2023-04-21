package com.example.webnovelservice.domain.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webnovelservice.domain.user.UserRepository;
import com.example.webnovelservice.domain.user.UserService;
import com.example.webnovelservice.domain.user.entity.User;
import com.example.webnovelservice.exception.BadRequestException;
import com.example.webnovelservice.model.dto.request.LoginRequest;
import com.example.webnovelservice.model.dto.request.SignUpRequest;
import com.example.webnovelservice.model.dto.response.AuthResponse;
import com.example.webnovelservice.model.enums.AuthProvider;
import com.example.webnovelservice.model.enums.UserRole;
import com.example.webnovelservice.response.SuccessResponse;
import com.example.webnovelservice.security.TokenProvider;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
        PasswordEncoder passwordEncoder, TokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // check if user exists and password match
        userService.checkUserExists(loginRequest);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public SuccessResponse<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        user.setUserRole(UserRole.USER);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        return new SuccessResponse<>(null, "User registered successfully!");
    }

}
