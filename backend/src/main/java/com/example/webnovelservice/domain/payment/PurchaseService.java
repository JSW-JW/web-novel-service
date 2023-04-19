package com.example.webnovelservice.domain.payment;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.webnovelservice.exception.InsufficientRemainedTokensException;
import com.example.webnovelservice.exception.ResourceNotFoundException;
import com.example.webnovelservice.model.dto.response.ChapterDto;
import com.example.webnovelservice.model.dto.response.PurchaseDto;
import com.example.webnovelservice.domain.novel.entity.Chapter;
import com.example.webnovelservice.domain.payment.entity.NovelTokenCounter;
import com.example.webnovelservice.domain.payment.entity.Purchase;
import com.example.webnovelservice.domain.user.entity.User;
import com.example.webnovelservice.domain.novel.ChapterRepository;
import com.example.webnovelservice.domain.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PurchaseService {
	private final PurchaseRepository purchaseRepository;
	private final ChapterRepository chapterRepository;
	private final UserRepository userRepository;
	private final NovelTokenCounterRepository novelTokenCounterRepository;
	private final ModelMapper modelMapper;

	public PurchaseService(PurchaseRepository purchaseRepository, ChapterRepository chapterRepository,
		UserRepository userRepository, NovelTokenCounterRepository novelTokenCounterRepository,
		ModelMapper modelMapper) {
		this.purchaseRepository = purchaseRepository;
		this.chapterRepository = chapterRepository;
		this.userRepository = userRepository;
		this.novelTokenCounterRepository = novelTokenCounterRepository;
		this.modelMapper = modelMapper;
	}

	@Transactional
	public PurchaseDto purchaseChapter(Long userId, Long chapterId) throws InsufficientRemainedTokensException {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Chapter chapter = chapterRepository.findById(chapterId)
			.orElseThrow(() -> new ResourceNotFoundException("Chapter", "chapter id", chapterId));

		NovelTokenCounter novelTokenCounter = novelTokenCounterRepository
			.findByUserIdAndNovelId(user.getId(), chapter.getNovel().getId())
			.orElseThrow(InsufficientRemainedTokensException::new);

		if (novelTokenCounter.getTokenCount() >= chapter.getTokensRequired()) {
			novelTokenCounter.setTokenCount(novelTokenCounter.getTokenCount() - chapter.getTokensRequired());
			novelTokenCounterRepository.save(novelTokenCounter);
		} else {
			throw new InsufficientRemainedTokensException();
		}

		Purchase purchase = new Purchase();
		purchase.setUser(user);
		purchase.setChapter(chapter);

		Purchase createdPurchase = purchaseRepository.save(purchase);

		ChapterDto chapterDto = modelMapper.map(chapter, ChapterDto.class);
		Integer tokensLeft = novelTokenCounter.getTokenCount();
		PurchaseDto purchaseDto = modelMapper.map(createdPurchase, PurchaseDto.class);
		purchaseDto.setChapter(chapterDto);
		purchaseDto.setTokensLeft(tokensLeft);

		return purchaseDto;
	}

}

