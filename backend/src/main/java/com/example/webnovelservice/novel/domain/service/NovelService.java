package com.example.webnovelservice.novel.domain.service;

import com.example.webnovelservice.auth.exception.UserNotFoundException;
import com.example.webnovelservice.commons.response.exception.ResourceNotFoundException;
import com.example.webnovelservice.chapter.domain.repository.ChapterRepository;
import com.example.webnovelservice.commons.util.S3Service;
import com.example.webnovelservice.novel.domain.entity.Genre;
import com.example.webnovelservice.novel.domain.entity.ShowcaseType;
import com.example.webnovelservice.novel.domain.repository.GenreRepository;
import com.example.webnovelservice.novel.dto.request.CreateNovelRequest;
import com.example.webnovelservice.novel.dto.response.NovelDto;
import com.example.webnovelservice.novel.domain.repository.NovelRepository;
import com.example.webnovelservice.novel.domain.entity.Novel;
import com.example.webnovelservice.novel.dto.response.ShowcaseNovelsDto;
import com.example.webnovelservice.user.domain.entity.User;
import com.example.webnovelservice.user.domain.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NovelService {

	private final UserRepository userRepository;
	private final NovelRepository novelRepository;
	private final GenreRepository genreRepository;
	private final ModelMapper modelMapper;
	private final S3Service s3Service;

	public NovelService(UserRepository userRepository, NovelRepository novelRepository, GenreRepository genreRepository,
		ModelMapper modelMapper, S3Service s3Service) {
		this.userRepository = userRepository;
		this.novelRepository = novelRepository;
		this.genreRepository = genreRepository;
		this.modelMapper = modelMapper;
		this.s3Service = s3Service;
	}

	@Cacheable(value = "bestNovelsCache", key = "#showcaseTypeIds + '-' + #numOfMostViewedNovels")
	public Map<String, ShowcaseNovelsDto> getNovelsByShowcaseTypes(List<Long> showcaseTypeIds,
		Integer numOfMostViewedNovels) {
		List<Novel> novels;
		if (showcaseTypeIds != null && !showcaseTypeIds.isEmpty()) {
			novels = novelRepository.findByShowcaseTypeIds(showcaseTypeIds);
		} else {
			novels = novelRepository.findAllInShowcase();
		}

		log.info("Cache call check: method body is getting called.");

		// get categorized result
		Map<String, ShowcaseNovelsDto> groupedNovels = novels.stream()
			.flatMap(novel -> novel.getShowcaseTypes()
				.stream()
				.map(st -> new AbstractMap.SimpleEntry<>(List.of(st.getName(), st.getDescription()),
					modelMapper.map(novel, NovelDto.class))))
			.collect(
				Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
			.entrySet()
			.stream()
			.collect(
				Collectors.toMap(e -> e.getKey().get(0), e -> new ShowcaseNovelsDto(e.getKey().get(1), e.getValue())));

		List<Long> excludeNovelIds = novels.stream().map(Novel::getId).collect(Collectors.toList());
		List<Novel> mostViewNovels = novelRepository.findTopNovels(excludeNovelIds,
			PageRequest.of(0, numOfMostViewedNovels));

		List<NovelDto> mostViewNovelsDto = mostViewNovels.stream()
			.map(novel -> modelMapper.map(novel, NovelDto.class))
			.collect(Collectors.toList());

		ShowcaseNovelsDto topViewedNovelsDto = new ShowcaseNovelsDto("Top Viewed", mostViewNovelsDto);

		groupedNovels.put("top_viewed", topViewedNovelsDto);

		return groupedNovels;
	}

	public List<NovelDto> getAllNovels() {
		List<Novel> novels = novelRepository.findAll();
		return novels.stream().map(novel -> modelMapper.map(novel, NovelDto.class)).collect(Collectors.toList());
	}

	public NovelDto registerNovel(CreateNovelRequest request, MultipartFile file, Long userId) throws IOException {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		// check genre ids and
		Set<Genre> genres = new HashSet<>();
		for (Long genreId : request.getGenreIds()) {
			Genre genre = genreRepository.findById(genreId)
				.orElseThrow(() -> new ResourceNotFoundException("Genre", "id", genreId));
			genres.add(genre);
		}

		Novel novel = new Novel();
		novel.setTitle(request.getTitle());
		novel.setDescription(request.getDescription());
		novel.setAuthor(user);

		// set genres
		novel.setGenres(genres);
		Novel createdNovel = novelRepository.save(novel);

		// TODO: upload s3
		s3Service.uploadFile(file);

		// TODO: save file

		return modelMapper.map(createdNovel, NovelDto.class);
	}

	public NovelDto getNovelById(Long id) {
		Novel novel = novelRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Novel", "novel id", id));
		return modelMapper.map(novel, NovelDto.class);
	}
}
