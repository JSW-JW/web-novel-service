package com.example.webnovelservice.service;

import org.springframework.stereotype.Service;

import com.example.webnovelservice.exception.ResourceNotFoundException;
import com.example.webnovelservice.model.entity.transaction.OwnershipTokenTransaction;
import com.example.webnovelservice.model.entity.user.User;
import com.example.webnovelservice.repository.OwnershipTokenTransactionRepository;
import com.example.webnovelservice.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OwnershipTokenService {
	private final OwnershipTokenTransactionRepository ownershipTokenTransactionRepository;
	private final UserRepository userRepository;
	public OwnershipTokenService(OwnershipTokenTransactionRepository ownershipTokenTransactionRepository, UserRepository userRepository) {
		this.ownershipTokenTransactionRepository = ownershipTokenTransactionRepository;
		this.userRepository = userRepository;
	}

	public void chargeOwnershipToken(Long userId, int tokens) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		OwnershipTokenTransaction ownershipTokenTransaction = new OwnershipTokenTransaction();
		ownershipTokenTransaction.setUser(user);
		ownershipTokenTransaction.setTokensPurchased(tokens);

		ownershipTokenTransactionRepository.save(ownershipTokenTransaction);
	}

	public Long getRemainingOwnershipTokens(User user) {
		User userWithToken = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));
		return userWithToken.getRemainedTokens();
	}

	@Transactional
	public boolean useOwnershipToken(User user) {
		Long remainingTokens = getRemainingOwnershipTokens(user);
		if (remainingTokens > 0) {
			user.setRemainedTokens(user.getRemainedTokens() - 1);
			userRepository.save(user);
			return true;
		}
		return false;
	}

}
