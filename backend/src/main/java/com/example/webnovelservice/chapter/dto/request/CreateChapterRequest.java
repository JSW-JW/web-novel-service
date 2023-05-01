package com.example.webnovelservice.chapter.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class CreateChapterRequest {
	private Long novelId;
	private String title;
	private String contents;

	private Integer tokensRequired;
	private Integer order;

	public CreateChapterRequest(Long novelId, String title, String contents, Integer tokensRequired) {
		this.novelId = novelId;
		this.title = title;
		this.contents = contents;
		this.tokensRequired = tokensRequired;
	}

	public CreateChapterRequest() {}
}
