package com.example.webnovelservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NovelDto {
	public NovelDto() {}
	private Long id;
	private String title;
	private String genre;
	private String description;

	private String showcaseType;
}


