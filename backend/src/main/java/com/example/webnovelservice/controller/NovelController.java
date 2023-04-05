package com.example.webnovelservice.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.webnovelservice.model.command.NovelCreateRequest;
import com.example.webnovelservice.model.dto.NovelDto;
import com.example.webnovelservice.model.entity.novel.Chapter;
import com.example.webnovelservice.model.entity.novel.Novel;
import com.example.webnovelservice.payload.novel.NovelDetailsResponse;
import com.example.webnovelservice.service.ChapterService;
import com.example.webnovelservice.service.NovelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/novels")
public class NovelController {

	private final NovelService novelService;
	private final ModelMapper modelMapper;

	public NovelController(NovelService novelService, ModelMapper modelMapper) {
		this.novelService = novelService;
		this.modelMapper = modelMapper;
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
}
