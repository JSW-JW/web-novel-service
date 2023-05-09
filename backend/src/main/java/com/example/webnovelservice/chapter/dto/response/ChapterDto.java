package com.example.webnovelservice.chapter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChapterDto {
	private Long id;
	private String title;
	private String contents;

	private Integer order;
	private Long novelId;
	private String thumbnailUrl;
}
