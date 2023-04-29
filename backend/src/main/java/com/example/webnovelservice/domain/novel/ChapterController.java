package com.example.webnovelservice.domain.novel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webnovelservice.model.dto.request.CreateChapterRequest;
import com.example.webnovelservice.model.dto.response.ChapterDto;
import com.example.webnovelservice.model.dto.response.NovelDetailsResponse;
import com.example.webnovelservice.security.CurrentUser;
import com.example.webnovelservice.security.UserPrincipal;

@RestController
@RequestMapping("/api/v1/chapters")
public class ChapterController {
	private final ChapterService chapterService;

	public ChapterController(ChapterService chapterService) {
		this.chapterService = chapterService;
	}

	@GetMapping("/list/{novelId}")
	public ResponseEntity<NovelDetailsResponse> getNovelDetails(@PathVariable Long novelId) {
		NovelDetailsResponse novelDetails = chapterService.getNovelAndChapters(novelId);
		return ResponseEntity.ok(novelDetails);
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
