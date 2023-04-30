package com.example.webnovelservice.payment.domain.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.webnovelservice.commons.response.exception.ResourceNotFoundException;
import com.example.webnovelservice.novel.domain.entity.Novel;
import com.example.webnovelservice.payment.domain.entity.NovelTokenCounter;
import com.example.webnovelservice.payment.domain.entity.OwnershipTokenTransaction;
import com.example.webnovelservice.payment.domain.repository.NovelTokenCounterRepository;
import com.example.webnovelservice.payment.domain.repository.OwnershipTokenTransactionRepository;
import com.example.webnovelservice.user.domain.entity.User;
import com.example.webnovelservice.novel.domain.repository.NovelRepository;
import com.example.webnovelservice.user.domain.repository.UserRepository;
import com.example.webnovelservice.payment.dto.request.CreateTokenTransactionRequest;
import com.example.webnovelservice.payment.dto.response.TokenTransactionDto;

@Service
public class OwnershipTokenTransactionService {
	private final OwnershipTokenTransactionRepository ownershipTokenTransactionRepository;
	private final UserRepository userRepository;
	private final NovelRepository novelRepository;
	private final NovelTokenCounterRepository novelTokenCounterRepository;
	private final ModelMapper modelMapper;

	public OwnershipTokenTransactionService(OwnershipTokenTransactionRepository ownershipTokenTransactionRepository,
		UserRepository userRepository, NovelRepository novelRepository,
		NovelTokenCounterRepository novelTokenCounterRepository,
		ModelMapper modelMapper) {
		this.ownershipTokenTransactionRepository = ownershipTokenTransactionRepository;
		this.userRepository = userRepository;
		this.novelRepository = novelRepository;
		this.novelTokenCounterRepository = novelTokenCounterRepository;
		this.modelMapper = modelMapper;
	}

	public TokenTransactionDto chargeOwnershipToken(Long userId, CreateTokenTransactionRequest request) {
		Long novelId = request.getNovelId();
		Integer tokensToCharge = request.getTokensToCharge();
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Novel novel = novelRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("Novel", "id", novelId));

		OwnershipTokenTransaction ownershipTokenTransaction = new OwnershipTokenTransaction();
		ownershipTokenTransaction.setUser(user);

		ownershipTokenTransaction.setTokensPurchased(request.getTokensToCharge());
		Integer price = TokenPolicyService.getPrice(request.getTokensToCharge(), novelId);
		ownershipTokenTransaction.setPrice(price);

		OwnershipTokenTransaction transaction = ownershipTokenTransactionRepository.save(ownershipTokenTransaction);

		Optional<NovelTokenCounter> novelTokenCounterOptional = novelTokenCounterRepository.findByUserIdAndNovelId(
			userId, novelId);
		NovelTokenCounter novelTokenCounter;
		if (novelTokenCounterOptional.isPresent()) {
			novelTokenCounter = novelTokenCounterOptional.get();
			novelTokenCounter.setTokenCount(novelTokenCounter.getTokenCount() + tokensToCharge);
		} else {
			novelTokenCounter = new NovelTokenCounter();
			novelTokenCounter.setUser(user);
			novelTokenCounter.setNovel(novel);
			novelTokenCounter.setTokenCount(tokensToCharge);
		}
		novelTokenCounterRepository.save(novelTokenCounter);

		return modelMapper.map(transaction, TokenTransactionDto.class);
	}

}
