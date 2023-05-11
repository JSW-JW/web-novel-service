package com.example.webnovelservice.user.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.webnovelservice.user.domain.repository.UserRepository;
import com.example.webnovelservice.user.domain.entity.User;
import com.example.webnovelservice.commons.response.exception.ResourceNotFoundException;
import com.example.webnovelservice.user.dto.request.LoginRequest;
import com.example.webnovelservice.user.dto.response.UserDto;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;

	public UserService(UserRepository userRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	public UserDto checkUserExists(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User with the email not found"));

		return modelMapper.map(user, UserDto.class);
	}
}
