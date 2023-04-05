package com.example.webnovelservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webnovelservice.exception.ResourceNotFoundException;
import com.example.webnovelservice.model.entity.novel.Chapter;
import com.example.webnovelservice.model.entity.transaction.OwnershipTokenTransaction;
import com.example.webnovelservice.model.entity.transaction.Purchase;
import com.example.webnovelservice.model.entity.user.User;
import com.example.webnovelservice.repository.ChapterRepository;
import com.example.webnovelservice.repository.OwnershipTokenTransactionRepository;

@Service
public class PurchaseService {
	@Autowired
	private PurchaseRepository purchaseRepository;
	@Autowired
	private ChapterRepository chapterRepository;
	@Autowired
	private OwnershipTokenTransactionRepository ownershipTokenTransactionRepository;

	public Purchase purchaseChapter(Long userId, Long chapterId) throws InsufficientOwnershipTokenException {
		Optional<OwnershipTokenTransaction> ownershipTokenTransactionOptional = ownershipTokenTransactionRepository.findByUserId(userId);

		if (ownershipTokenTransactionOptional.isPresent()) {
			OwnershipTokenTransaction ownershipTokenTransaction = ownershipTokenTransactionOptional.get();
			int remainingTokens = ownershipTokenTransaction.getTokensPurchased();

			Chapter chapter = chapterRepository.findById(chapterId)
				.orElseThrow(() -> new ResourceNotFoundException("Chapter", "chapter id", chapterId));


			if (remainingTokens == 0) {
				throw new InsufficientOwnershipTokenException();
			}

			Purchase purchase = new Purchase();
			purchase.setUser(new User(userId));
			purchase.setChapter(chapter);

			purchaseRepository.save(purchase);

			ownershipTokenTransaction.setTokensPurchased(remainingTokens - 1);
			ownershipTokenTransactionRepository.save(ownershipTokenTransaction);
		} else {
			throw new InsufficientOwnershipTokenException();
		}
		return null;
	}
}
