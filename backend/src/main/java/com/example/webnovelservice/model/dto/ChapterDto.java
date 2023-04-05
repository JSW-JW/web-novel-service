package com.example.webnovelservice.model.dto;

import lombok.Builder;

@Builder
public class ChapterDto {
	private Long id;
	private String title;
	private String content;
	private Long novelId;
}
