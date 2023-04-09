package com.example.webnovelservice.domain.payment;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.webnovelservice.exception.ResourceNotFoundException;
import com.example.webnovelservice.model.entity.novel.Novel;
import com.example.webnovelservice.model.entity.transaction.NovelTokenCounter;
import com.example.webnovelservice.model.entity.transaction.OwnershipTokenTransaction;
import com.example.webnovelservice.model.entity.user.User;
import com.example.webnovelservice.domain.novel.NovelRepository;
import com.example.webnovelservice.domain.payment.NovelTokenCounterRepository;
import com.example.webnovelservice.domain.payment.OwnershipTokenTransactionRepository;
import com.example.webnovelservice.domain.user.UserRepository;

@Service
public class OwnershipTokenService {
	private final OwnershipTokenTransactionRepository ownershipTokenTransactionRepository;
	private final UserRepository userRepository;
	private final NovelRepository novelRepository;
	private final NovelTokenCounterRepository novelTokenCounterRepository;

	public OwnershipTokenService(OwnershipTokenTransactionRepository ownershipTokenTransactionRepository,
		UserRepository userRepository, NovelRepository novelRepository,
		NovelTokenCounterRepository novelTokenCounterRepository) {
		this.ownershipTokenTransactionRepository = ownershipTokenTransactionRepository;
		this.userRepository = userRepository;
		this.novelRepository = novelRepository;
		this.novelTokenCounterRepository = novelTokenCounterRepository;
	}

	public void chargeOwnershipToken(Long userId, int tokens, int price, Long novelId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Novel novel = novelRepository.findById(novelId)
			.orElseThrow(() -> new ResourceNotFoundException("Novel", "id", novelId));

		OwnershipTokenTransaction ownershipTokenTransaction = new OwnershipTokenTransaction();
		ownershipTokenTransaction.setUser(user);
		ownershipTokenTransaction.setTokensPurchased(tokens);
		ownershipTokenTransaction.setPrice(price);

		ownershipTokenTransactionRepository.save(ownershipTokenTransaction);

		Optional<NovelTokenCounter> novelTokenCounterOptional = novelTokenCounterRepository.findByUserIdAndNovelId(
			userId, novelId);
		if (novelTokenCounterOptional.isPresent()) {
			NovelTokenCounter novelTokenCounter = novelTokenCounterOptional.get();
			novelTokenCounter.setTokenCount(novelTokenCounter.getTokenCount() + tokens);
			novelTokenCounterRepository.save(novelTokenCounter);
		} else {
			NovelTokenCounter novelTokenCounter = new NovelTokenCounter();
			novelTokenCounter.setUser(user);
			novelTokenCounter.setNovel(novel);
			novelTokenCounter.setTokenCount(tokens);
			novelTokenCounterRepository.save(novelTokenCounter);
		}
	}

}
