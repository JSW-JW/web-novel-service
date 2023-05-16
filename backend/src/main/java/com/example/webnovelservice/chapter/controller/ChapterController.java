package com.example.webnovelservice.chapter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webnovelservice.chapter.dto.request.CreateChapterRequest;
import com.example.webnovelservice.chapter.dto.response.ChapterDto;
import com.example.webnovelservice.commons.response.ErrorState;
import com.example.webnovelservice.commons.response.ResponseEntityBuilder;
import com.example.webnovelservice.novel.dto.response.NovelDetailsDto;
import com.example.webnovelservice.commons.annotation.CurrentUser;
import com.example.webnovelservice.commons.security.UserPrincipal;
import com.example.webnovelservice.chapter.domain.service.ChapterService;

@RestController
@RequestMapping("/api/v1/chapters")
public class ChapterController {
	private final ChapterService chapterService;

	public ChapterController(ChapterService chapterService) {
		this.chapterService = chapterService;
	}

	@GetMapping("/list/{novelId}")
	public ResponseEntity<Object> getNovelDetails(@PathVariable Long novelId) {
		NovelDetailsDto novelDetails = chapterService.getNovelAndChapters(novelId);
		return ResponseEntityBuilder.success(HttpStatus.OK, novelDetails);
	}

	@PostMapping
	public ResponseEntity<ChapterDto> registerChapterForNovel(@RequestBody CreateChapterRequest createChapterRequest) {
		ChapterDto chapterDto = chapterService.registerChapterForNovel(createChapterRequest);
		return ResponseEntity.ok(chapterDto);
	}

	@GetMapping("/{chapterId}")
	public ResponseEntity<ChapterDto> getChapterByChapterId(@PathVariable Long chapterId, @CurrentUser UserPrincipal userPrincipal) {
		ChapterDto chapterDto = chapterService.getChapterByChapterId(chapterId, userPrincipal.getId());
		return ResponseEntity.ok(chapterDto);
	}
}
