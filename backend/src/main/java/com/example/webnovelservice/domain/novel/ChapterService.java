package com.example.webnovelservice.domain.novel;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.webnovelservice.domain.novel.entity.Chapter;
import com.example.webnovelservice.domain.novel.entity.Novel;
import com.example.webnovelservice.domain.payment.PurchaseRepository;
import com.example.webnovelservice.exception.ResourceNotFoundException;
import com.example.webnovelservice.model.dto.request.CreateChapterRequest;
import com.example.webnovelservice.model.dto.response.ChapterDto;
import com.example.webnovelservice.model.dto.response.NovelDetailsResponse;

@Service
public class ChapterService {

	private final ChapterRepository chapterRepository;
	private final NovelRepository novelRepository;
	private final PurchaseRepository purchaseRepository;
	private final ModelMapper modelMapper;

	public ChapterService(ChapterRepository chapterRepository, NovelRepository novelRepository,
		PurchaseRepository purchaseRepository, ModelMapper modelMapper) {
		this.chapterRepository = chapterRepository;
		this.novelRepository = novelRepository;
		this.purchaseRepository = purchaseRepository;
		this.modelMapper = modelMapper;

		// Add custom mapping for Chapter to ChapterDto
		modelMapper.createTypeMap(Chapter.class, ChapterDto.class)
			.addMapping(src -> src.getNovel().getId(), ChapterDto::setNovelId);
	}

	public NovelDetailsResponse getNovelAndChapters(Long novelId) {
		Novel novel = novelRepository.findById(novelId)
			.orElseThrow(() -> new ResourceNotFoundException("Novel", "novel id", novelId));

		List<Chapter> chapters = chapterRepository.findByNovelId(novelId);
		List<ChapterDto> listOfChapterDto = chapters.stream()
			.map(chapter -> modelMapper.map(chapter, ChapterDto.class))
			.collect(Collectors.toList());

		return new NovelDetailsResponse(novel, listOfChapterDto);
	}

	public ChapterDto registerChapterForNovel(CreateChapterRequest request) {
		Long novelId = request.getNovelId();
		Novel novel = novelRepository.findById(novelId)
			.orElseThrow(() -> new ResourceNotFoundException("Novel", "novel id", novelId));

		// get the number of chapters related with the novel id
		Integer chapterNums = chapterRepository.countByNovelId(novelId);
		// set the order of chapter in request variable
		request.setOrder(chapterNums + 1);

		Chapter chapter = modelMapper.map(request, Chapter.class);
		chapter.setNovel(novel);

		Chapter createdChapter = chapterRepository.save(chapter);

		return modelMapper.map(createdChapter, ChapterDto.class);
	}

	public ChapterDto getChapterByChapterId(Long chapterId, Long userId) {
		Chapter chapter = chapterRepository.findById(chapterId)
			.orElseThrow(() -> new ResourceNotFoundException("Chapter", "chapter id", chapterId));

		purchaseRepository.findByUserIdAndChapterId(userId, chapterId)
			.orElseThrow(() -> new ResourceNotFoundException("Not purchased chapter"));

		return modelMapper.map(chapter, ChapterDto.class);
	}
}
