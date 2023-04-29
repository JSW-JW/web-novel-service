package com.example.webnovelservice.domain.novel;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import com.example.webnovelservice.model.dto.request.CreateNovelRequest;
import com.example.webnovelservice.model.dto.response.NovelDto;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/novels")
public class NovelController {
	private final NovelService novelService;

	public NovelController(NovelService novelService) {
		this.novelService = novelService;
	}

	@PostMapping
	@PreAuthorize("hasRole('AUTHOR')") // intercept the request before reaching this controller.
	public ResponseEntity<NovelDto> registerNovel(@Valid @RequestBody CreateNovelRequest createNovelRequest, HttpServletResponse response) {
		NovelDto novelDto = novelService.registerNovel(createNovelRequest);
		return ResponseEntity.ok(novelDto);
	}

	@GetMapping
	public ResponseEntity<List<NovelDto>> getAllNovels() {
		List<NovelDto> novelDtos = novelService.getAllNovels();
		return ResponseEntity.ok(novelDtos);
	}

	@Operation(summary = "get list of novels in showcase", description =
		"""
			showcaseTypeIds is null: all novels in showcase
			showcaseTypeIds is not null: novels matching with the showcaseTypeIds""")
	@PostMapping("/home-best")
	public ResponseEntity<Map<String, List<NovelDto>>> getBestNovels(@RequestBody List<Long> showcaseTypeIds) {
		Map<String, List<NovelDto>> novels = novelService.getNovelsByShowcaseTypes(showcaseTypeIds);
		return ResponseEntity.ok(novels);
	}
}
