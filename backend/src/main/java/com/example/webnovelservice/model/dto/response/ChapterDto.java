package com.example.webnovelservice.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChapterDto {
	private Long id;
	private String title;
	private String contents;

	private Integer order;
	private Long novelId;

	public ChapterDto(Long id, String title, String contents, Integer order, Long novelId) {
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.order = order;
		this.novelId = novelId;
	}

	public ChapterDto() {}
}
