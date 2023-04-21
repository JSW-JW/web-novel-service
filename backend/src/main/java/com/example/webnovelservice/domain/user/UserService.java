package com.example.webnovelservice.domain.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.webnovelservice.domain.user.entity.User;
import com.example.webnovelservice.exception.ResourceNotFoundException;
import com.example.webnovelservice.model.dto.request.LoginRequest;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}


	public void checkUserExists(LoginRequest loginRequest) {
		User user = userRepository.findByEmail(loginRequest.getEmail())
			.orElseThrow(() -> new ResourceNotFoundException("User with the email not found"));

		boolean isPasswordMatched = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
		if (!isPasswordMatched) {
			throw new ResourceNotFoundException("User with the password not found");
		}
	}
}
