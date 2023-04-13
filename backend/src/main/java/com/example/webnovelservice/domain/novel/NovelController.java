package com.example.webnovelservice.domain.novel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import com.example.webnovelservice.model.command.RegisterNovelRequest;
import com.example.webnovelservice.model.dto.NovelDto;
import com.example.webnovelservice.response.SuccessResponse;

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
	public SuccessResponse<NovelDto> registerNovel(@Valid @RequestBody RegisterNovelRequest registerNovelRequest, HttpServletResponse response) {
		NovelDto novelDto = novelService.registerNovel(registerNovelRequest);
		return new SuccessResponse<>(novelDto);
	}

	@GetMapping
	public SuccessResponse<List<NovelDto>> getAllNovels() {
		List<NovelDto> novelDtos = novelService.getAllNovels();
		return new SuccessResponse<>(novelDtos);
	}

	@Operation(summary = "get list of novels in showcase", description =
		"""
			showcaseTypeIds is null: all novels in showcase
			showcaseTypeIds is not null: novels matching with the showcaseTypeIds""")
	@PostMapping("/home-best")
	public SuccessResponse<Map<String, List<NovelDto>>> getBestNovels(@RequestBody List<Long> showcaseTypeIds) {
		Map<String, List<NovelDto>> novels = novelService.getNovelsByShowcaseTypes(showcaseTypeIds);
		return new SuccessResponse<>(novels);
	}
}
