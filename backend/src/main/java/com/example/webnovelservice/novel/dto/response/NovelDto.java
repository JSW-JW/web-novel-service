package com.example.webnovelservice.novel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NovelDto {
	private Long id;
	private String title;

	private String thumbnailUrl;
	private String genre;
	private String description;
}


