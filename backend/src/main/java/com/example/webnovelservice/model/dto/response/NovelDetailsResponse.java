package com.example.webnovelservice.model.dto.response;


import java.util.List;

import com.example.webnovelservice.domain.novel.entity.Chapter;
import com.example.webnovelservice.domain.novel.entity.Novel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NovelDetailsResponse {
	private Novel novel;
	private List<ChapterDto> chapters;

	public NovelDetailsResponse(Novel novel, List<ChapterDto> chapters) {
		this.novel = novel;
		this.chapters = chapters;
	}

	public NovelDetailsResponse() {
	}
}
