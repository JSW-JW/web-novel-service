package com.example.webnovelservice.domain.novel;


import org.springframework.stereotype.Service;

import java.util.List;

import com.example.webnovelservice.domain.novel.entity.Chapter;

@Service
public class ChapterService {

	private final ChapterRepository chapterRepository;

	public ChapterService(ChapterRepository chapterRepository) {
		this.chapterRepository = chapterRepository;
	}

	public List<Chapter> getChaptersByNovelId(Long novelId) {
		return chapterRepository.findByNovelId(novelId);
	}
}
