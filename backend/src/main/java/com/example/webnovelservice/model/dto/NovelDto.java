package com.example.webnovelservice.model.dto;

import java.util.List;

import lombok.Builder;

@Builder
public class NovelDto {
	private Long id;
	private String title;
	private String description;
	private String genre;
	private Long authorId;
	private List<Long> chapterIds;
}


