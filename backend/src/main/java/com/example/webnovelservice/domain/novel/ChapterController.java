package com.example.webnovelservice.domain.novel;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webnovelservice.model.dto.request.CreateChapterRequest;
import com.example.webnovelservice.model.dto.response.ChapterDto;
import com.example.webnovelservice.response.SuccessResponse;
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
	public SuccessResponse<List<ChapterDto>> getChaptersByNovelId(@PathVariable Long novelId) {
		List<ChapterDto> chapters = chapterService.getChaptersByNovelId(novelId);
		return new SuccessResponse<>(chapters);
	}

	@PostMapping
	public SuccessResponse<ChapterDto> registerChapterForNovel(@RequestBody CreateChapterRequest createChapterRequest) {
		ChapterDto chapterDto = chapterService.registerChapterForNovel(createChapterRequest);
		return new SuccessResponse<>(chapterDto);
	}

	@GetMapping("/{chapterId}")
	public SuccessResponse<ChapterDto> getChapterByChapterId(@PathVariable Long chapterId, @CurrentUser UserPrincipal userPrincipal) {
		ChapterDto chapterDto = chapterService.getChapterByChapterId(chapterId, userPrincipal.getId());
		return new SuccessResponse<>(chapterDto);
	}
}
