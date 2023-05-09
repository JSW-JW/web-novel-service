package com.example.webnovelservice.novel.domain.service;


import com.example.webnovelservice.commons.response.exception.ResourceNotFoundException;
import com.example.webnovelservice.chapter.domain.repository.ChapterRepository;
import com.example.webnovelservice.novel.domain.entity.ShowcaseType;
import com.example.webnovelservice.novel.dto.request.CreateNovelRequest;
import com.example.webnovelservice.novel.dto.response.NovelDto;
import com.example.webnovelservice.novel.domain.repository.NovelRepository;
import com.example.webnovelservice.novel.domain.entity.Novel;
import com.example.webnovelservice.novel.dto.response.ShowcaseNovelsDto;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NovelService {

	private final NovelRepository novelRepository;
	private final ChapterRepository chapterRepository;
	private final ModelMapper modelMapper;

	public NovelService(NovelRepository novelRepository, ChapterRepository chapterRepository, ModelMapper modelMapper) {
		this.novelRepository = novelRepository;
		this.chapterRepository = chapterRepository;
		this.modelMapper = modelMapper;
	}

	// @Cacheable(value = "bestNovelsCache", key = "#showcaseTypeIds + '-' + #numOfMostViewedNovels")
	public Map<String, ShowcaseNovelsDto> getNovelsByShowcaseTypes(List<Long> showcaseTypeIds, Integer numOfMostViewedNovels) {
		List<Novel> novels;
		if (showcaseTypeIds != null && !showcaseTypeIds.isEmpty()) {
			novels = novelRepository.findByShowcaseTypeIds(showcaseTypeIds);
		} else {
			novels = novelRepository.findAllInShowcase();
		}

		log.info("Cache call check: method body is getting called.");

		// get categorized result
		Map<String, ShowcaseNovelsDto> groupedNovels = novels.stream()
			.flatMap(novel -> novel.getShowcaseTypes().stream().map(st -> new AbstractMap.SimpleEntry<>(List.of(st.getName(), st.getDescription()), modelMapper.map(novel, NovelDto.class))))
			.collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
			.entrySet().stream()
			.collect(Collectors.toMap(e -> e.getKey().get(0), e -> new ShowcaseNovelsDto(e.getKey().get(1), e.getValue())));

		List<Long> excludeNovelIds = novels.stream().map(Novel::getId).collect(Collectors.toList());
		List<Novel> mostViewNovels = novelRepository.findTopNovels(excludeNovelIds, PageRequest.of(0, numOfMostViewedNovels));

		List<NovelDto> mostViewNovelsDto = mostViewNovels.stream()
			.map(novel -> modelMapper.map(novel, NovelDto.class))
			.collect(Collectors.toList());

		ShowcaseNovelsDto topViewedNovelsDto = new ShowcaseNovelsDto("Top Viewed", mostViewNovelsDto);

		groupedNovels.put("top_viewed", topViewedNovelsDto);

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
