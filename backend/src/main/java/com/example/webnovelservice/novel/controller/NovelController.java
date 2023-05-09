package com.example.webnovelservice.novel.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.webnovelservice.commons.response.ResponseEntityBuilder;
import com.example.webnovelservice.novel.domain.service.NovelService;
import com.example.webnovelservice.novel.dto.request.CreateNovelRequest;
import com.example.webnovelservice.novel.dto.response.NovelDto;
import com.example.webnovelservice.novel.dto.response.ShowcaseNovelsDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
	public ResponseEntity<NovelDto> registerNovel(@Valid @RequestBody CreateNovelRequest createNovelRequest) {
		NovelDto novelDto = novelService.registerNovel(createNovelRequest);
		return ResponseEntity.ok(novelDto);
	}

	@GetMapping
	public ResponseEntity<List<NovelDto>> getAllNovels() {
		List<NovelDto> novelDtos = novelService.getAllNovels();
		return ResponseEntity.ok(novelDtos);
	}

	@Operation(summary = "get list of novels by showcaseType ids", description =
			"""
			showcaseTypeIds is null - all novels in showcase <br>
			showcaseTypeIds is not null - novels matching with the showcaseTypeIds
			"""
	)
	@GetMapping("/home-best")
	public ResponseEntity<Object> getBestNovels(
		@Parameter(description = "Comma separated integer values for expected showcaseType id",
			example = "1,2",
			style = ParameterStyle.SIMPLE, explode = Explode.FALSE)
		@RequestParam(value = "showcaseTypeIds", required = false) List<Long> ids) {
		Integer numOfMostViewedNovels = 10;

		// get novels of showcase type ids and most viewed ones
		Map<String, ShowcaseNovelsDto> novels = novelService.getNovelsByShowcaseTypes(ids, numOfMostViewedNovels);
		return ResponseEntityBuilder.build("Successfully retrieved home best novels", HttpStatus.OK, novels);
	}
}
