package com.example.webnovelservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.webnovelservice.exception.InsufficientRemainedTokensException;
import com.example.webnovelservice.exception.ResourceNotFoundException;
import com.example.webnovelservice.model.dto.PurchaseDto;
import com.example.webnovelservice.model.entity.novel.Chapter;
import com.example.webnovelservice.model.entity.transaction.NovelTokenCounter;
import com.example.webnovelservice.model.entity.transaction.Purchase;
import com.example.webnovelservice.model.entity.user.User;
import com.example.webnovelservice.repository.ChapterRepository;
import com.example.webnovelservice.repository.NovelTokenCounterRepository;
import com.example.webnovelservice.repository.PurchaseRepository;
import com.example.webnovelservice.repository.UserRepository;

@Service
public class PurchaseService {
	private final PurchaseRepository purchaseRepository;
	private final ChapterRepository chapterRepository;
	private final UserRepository userRepository;
	private final NovelTokenCounterRepository novelTokenCounterRepository;

	public PurchaseService(PurchaseRepository purchaseRepository, ChapterRepository chapterRepository,
		UserRepository userRepository, NovelTokenCounterRepository novelTokenCounterRepository) {
		this.purchaseRepository = purchaseRepository;
		this.chapterRepository = chapterRepository;
		this.userRepository = userRepository;
		this.novelTokenCounterRepository = novelTokenCounterRepository;
	}

	public PurchaseDto purchaseChapter(Long userId, Long chapterId) throws InsufficientRemainedTokensException {
		Optional<User> userOptional = userRepository.findById(userId);
		if (userOptional.isEmpty()) {
			throw new ResourceNotFoundException("User", "id", userId);
		}
		User user = userOptional.get();

		Chapter chapter = chapterRepository.findById(chapterId)
			.orElseThrow(() -> new ResourceNotFoundException("Chapter", "chapter id", chapterId));

		NovelTokenCounter novelTokenCounter = novelTokenCounterRepository.findByUserIdAndNovelId(user.getId(),
				chapter.getNovel().getId())
			.orElseThrow(InsufficientRemainedTokensException::new);

		if (novelTokenCounter.getTokenCount() > 0) {
			novelTokenCounter.setTokenCount(novelTokenCounter.getTokenCount() - 1);
			novelTokenCounterRepository.save(novelTokenCounter);
		} else {
			throw new InsufficientRemainedTokensException();
		}

		Purchase purchase = new Purchase();
		purchase.setUser(user);
		purchase.setChapter(chapter);

		purchaseRepository.save(purchase);

		return PurchaseDto.builder().id(purchase.getId()).userId(user.getId()).chapterId(chapter.getId()).build();
	}

}

