package com.example.webnovelservice.service;


import com.example.webnovelservice.exception.ResourceNotFoundException;
import com.example.webnovelservice.model.command.NovelCreateRequest;
import com.example.webnovelservice.model.dto.ChapterDto;
import com.example.webnovelservice.model.dto.NovelDto;
import com.example.webnovelservice.model.entity.novel.Chapter;
import com.example.webnovelservice.model.entity.novel.Novel;
import com.example.webnovelservice.repository.ChapterRepository;
import com.example.webnovelservice.repository.NovelRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NovelService {

	private final NovelRepository novelRepository;
	private final ChapterRepository chapterRepository;
	private final ModelMapper modelMapper;

	public NovelService(NovelRepository novelRepository, ChapterRepository chapterRepository, ModelMapper modelMapper) {
		this.novelRepository = novelRepository;
		this.chapterRepository = chapterRepository;
		this.modelMapper = modelMapper;
	}

	public List<NovelDto> getNovelsByShowcaseTypes(List<Long> showcaseTypeIds) {
		List<Novel> novels;
		if (showcaseTypeIds != null && !showcaseTypeIds.isEmpty()) {
			novels = novelRepository.findByShowcaseTypeIds(showcaseTypeIds);
		} else {
			novels = novelRepository.findAllInShowcase();
		}
		return novels.stream()
			.map(novel -> modelMapper.map(novel, NovelDto.class))
			.collect(Collectors.toList());
	}

	public List<NovelDto> getAllNovels() {
		List<Novel> novels = novelRepository.findAll();
		return novels.stream()
			.map(novel -> modelMapper.map(novel, NovelDto.class))
			.collect(Collectors.toList());
	}

	public NovelDto createNovel(NovelCreateRequest request) {
		Novel novel = modelMapper.map(request, Novel.class);
		Novel createdNovel = novelRepository.save(novel);
		return modelMapper.map(createdNovel, NovelDto.class);
	}

	public NovelDto getNovelById(Long id) {
		Novel novel = novelRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Novel", "novel id", id));
		return modelMapper.map(novel, NovelDto.class);
	}

	public ChapterDto createChapterForNovel(Long novelId, ChapterDto chapterDTO) {
		Novel novel = novelRepository.findById(novelId)
			.orElseThrow(() -> new ResourceNotFoundException("Novel", "novel id", novelId));

		Chapter chapter = modelMapper.map(chapterDTO, Chapter.class);
		chapter.setNovel(novel);

		Chapter createdChapter = chapterRepository.save(chapter);

		return modelMapper.map(createdChapter, ChapterDto.class);
	}

	public List<ChapterDto> getChaptersForNovel(Long novelId) {
		Novel novel = novelRepository.findById(novelId)
			.orElseThrow(() -> new ResourceNotFoundException("Novel", "novel id", novelId));

		Set<Chapter> chapters = novel.getChapters();
		return chapters.stream()
			.map(chapter -> modelMapper.map(chapter, ChapterDto.class))
			.collect(Collectors.toList());
	}
}
