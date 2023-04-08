package com.example.webnovelservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.webnovelservice.model.entity.novel.Chapter;
import com.example.webnovelservice.repository.ChapterRepository;

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
