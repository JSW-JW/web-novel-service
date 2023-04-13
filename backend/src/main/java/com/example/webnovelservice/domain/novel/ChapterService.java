package com.example.webnovelservice.domain.novel;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.webnovelservice.domain.novel.entity.Chapter;
import com.example.webnovelservice.domain.novel.entity.Novel;
import com.example.webnovelservice.exception.ResourceNotFoundException;
import com.example.webnovelservice.model.command.RegisterChapterRequest;
import com.example.webnovelservice.model.dto.ChapterDto;

@Service
public class ChapterService {

	private final ChapterRepository chapterRepository;
	private final NovelRepository novelRepository;
	private final ModelMapper modelMapper;

	public ChapterService(ChapterRepository chapterRepository, NovelRepository novelRepository, ModelMapper modelMapper) {
		this.chapterRepository = chapterRepository;
		this.novelRepository = novelRepository;
		this.modelMapper = modelMapper;

		// Add custom mapping for Chapter to ChapterDto
		modelMapper.createTypeMap(Chapter.class, ChapterDto.class)
			.addMapping(src -> src.getNovel().getId(), ChapterDto::setNovelId);
	}

	public List<ChapterDto> getChaptersByNovelId(Long novelId) {
		novelRepository.findById(novelId)
			.orElseThrow(() -> new ResourceNotFoundException("Novel", "novel id", novelId));

		List<Chapter> chapters = chapterRepository.findByNovelId(novelId);
		return chapters.stream()
			.map(chapter -> modelMapper.map(chapter, ChapterDto.class))
			.collect(Collectors.toList());
	}

	public ChapterDto registerChapterForNovel(RegisterChapterRequest request) {
		Long novelId = request.novelId();
		Novel novel = novelRepository.findById(novelId)
			.orElseThrow(() -> new ResourceNotFoundException("Novel", "novel id", novelId));

		Chapter chapter = modelMapper.map(request, Chapter.class);
		chapter.setNovel(novel);

		Chapter createdChapter = chapterRepository.save(chapter);

		return modelMapper.map(createdChapter, ChapterDto.class);
	}
}
