package com.example.webnovelservice.model.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class RegisterChapterRequest {
	private Long novelId;
	private String title;
	private String contents;

	private Integer order;

	public RegisterChapterRequest(Long novelId, String title, String contents) {
		this.novelId = novelId;
		this.title = title;
		this.contents = contents;
	}

	public RegisterChapterRequest() {}
}
