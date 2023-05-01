package com.example.webnovelservice.novel.domain.service;


import com.example.webnovelservice.commons.response.exception.ResourceNotFoundException;
import com.example.webnovelservice.chapter.domain.repository.ChapterRepository;
import com.example.webnovelservice.novel.dto.request.CreateNovelRequest;
import com.example.webnovelservice.novel.dto.response.NovelDto;
import com.example.webnovelservice.novel.domain.repository.NovelRepository;
import com.example.webnovelservice.novel.domain.entity.Novel;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

	public Map<String, List<NovelDto>> getNovelsByShowcaseTypes(List<Long> showcaseTypeIds) {
		List<Novel> novels;
		if (showcaseTypeIds != null && !showcaseTypeIds.isEmpty()) {
			novels = novelRepository.findByShowcaseTypeIds(showcaseTypeIds);
		} else {
			novels = novelRepository.findAllInShowcase();
		}

		Map<String, List<NovelDto>> groupedNovels = novels.stream()
			.flatMap(novel -> novel.getShowcaseTypes().stream().map(showcaseType -> {
				NovelDto novelDto = modelMapper.map(novel, NovelDto.class);
				novelDto.setShowcaseType(showcaseType.getListName());
				return novelDto;
			}))
			.collect(Collectors.groupingBy(NovelDto::getShowcaseType));

		return groupedNovels;
	}

	public List<NovelDto> getAllNovels() {
		List<Novel> novels = novelRepository.findAll();
		return novels.stream()
			.map(novel -> modelMapper.map(novel, NovelDto.class))
			.collect(Collectors.toList());
	}

	public NovelDto registerNovel(CreateNovelRequest request) {
		Novel novel = modelMapper.map(request, Novel.class);
		Novel createdNovel = novelRepository.save(novel);
		return modelMapper.map(createdNovel, NovelDto.class);
	}

	public NovelDto getNovelById(Long id) {
		Novel novel = novelRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Novel", "novel id", id));
		return modelMapper.map(novel, NovelDto.class);
	}
}
