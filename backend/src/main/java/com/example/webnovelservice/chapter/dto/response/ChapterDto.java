package com.example.webnovelservice.chapter.dto.response;

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
	private String thumbnailUrl;

	public ChapterDto(Long id, String title, String contents, Integer order, Long novelId, String thumbnailUrl) {
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.order = order;
		this.novelId = novelId;
		this.thumbnailUrl = thumbnailUrl;
	}

	public ChapterDto() {}
}
