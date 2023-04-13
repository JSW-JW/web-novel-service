package com.example.webnovelservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChapterDto {
	private Long id;
	private String title;
	private String contents;
	private Long novelId;

	public ChapterDto(Long id, String title, String contents, Long novelId) {
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.novelId = novelId;
	}

	public ChapterDto() {}
}
