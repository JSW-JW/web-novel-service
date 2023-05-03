package com.example.webnovelservice.novel.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NovelDto {
	public NovelDto() {}
	private Long id;
	private String title;

	private String thumbnailUrl;
	private String genre;
	private String description;

	private String showcaseType;

	public NovelDto(Long id, String title, String genre, String description, String showcaseType, String thumbnailUrl) {
		this.id = id;
		this.title = title;
		this.thumbnailUrl = thumbnailUrl;
		this.genre = genre;
		this.description = description;
		this.showcaseType = showcaseType;
	}
}


