package com.example.webnovelservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.webnovelservice.model.command.NovelCreateRequest;
import com.example.webnovelservice.model.dto.NovelDto;
import com.example.webnovelservice.service.NovelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/novels")
public class NovelController {

	private final NovelService novelService;

	public NovelController(NovelService novelService) {
		this.novelService = novelService;
	}

	@PostMapping
	public ResponseEntity<NovelDto> createNovel(@Valid @RequestBody NovelCreateRequest novelCreateRequest) {
		NovelDto novelDto = novelService.createNovel(novelCreateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(novelDto);
	}

	@GetMapping
	public ResponseEntity<List<NovelDto>> getAllNovels() {
		List<NovelDto> novelDtos = novelService.getAllNovels();
		return ResponseEntity.ok(novelDtos);
	}

	@PostMapping("/home-best")
	public ResponseEntity<List<NovelDto>> homeBest(@RequestBody List<Long> showcaseTypeIds) {
		List<NovelDto> novelDtos = novelService.getNovelsByShowcaseTypes(showcaseTypeIds);
		return ResponseEntity.ok(novelDtos);
	}
}
