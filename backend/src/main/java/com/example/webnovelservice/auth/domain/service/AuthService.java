package com.example.webnovelservice.auth.domain.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.webnovelservice.auth.enums.AuthProvider;
import com.example.webnovelservice.auth.exception.UserNotFoundException;
import com.example.webnovelservice.commons.security.TokenProvider;
import com.example.webnovelservice.user.domain.entity.User;
import com.example.webnovelservice.user.domain.repository.UserRepository;
import com.example.webnovelservice.user.dto.request.LoginRequest;
import com.example.webnovelservice.user.dto.request.SignUpRequest;
import com.example.webnovelservice.user.enums.UserRole;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final TokenProvider tokenProvider;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
		AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
	}

	public void checkIfUSerExists(String email) {
		userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("User", "email", email));
	}

	public String authenticateAndReturnAccessToken(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				loginRequest.getEmail(),
				loginRequest.getPassword()
			)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return tokenProvider.createToken(authentication);
	}

	public String authenticateAndReturnAccessToken(SignUpRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				loginRequest.getEmail(),
				loginRequest.getPassword()
			)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return tokenProvider.createToken(authentication);
	}

	public String registerUser(SignUpRequest signUpRequest) {
		// check if user exists
		this.checkIfUSerExists(signUpRequest.getEmail());

		// Creating user's account
		User user = new User();
		user.setName(signUpRequest.getName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.setProvider(AuthProvider.local);

		user.setUserRole(UserRole.USER);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);

		// set authentication object in security contextHolder
		return authenticateAndReturnAccessToken(signUpRequest);
	}
}
