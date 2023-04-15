package com.example.webnovelservice.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class CreateChapterRequest {
	private Long novelId;
	private String title;
	private String contents;

	private Integer order;

	public CreateChapterRequest(Long novelId, String title, String contents) {
		this.novelId = novelId;
		this.title = title;
		this.contents = contents;
	}

	public CreateChapterRequest() {}
}
