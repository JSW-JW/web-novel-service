package com.example.webnovelservice.chapter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class CreateChapterRequest {
	private Long novelId;
	private String title;
	private String contents;

	private Integer tokensRequired;
	private Integer order;
}
