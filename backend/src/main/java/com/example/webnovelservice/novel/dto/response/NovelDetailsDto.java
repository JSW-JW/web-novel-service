package com.example.webnovelservice.novel.dto.response;


import java.util.List;

import com.example.webnovelservice.chapter.dto.response.ChapterDto;
import com.example.webnovelservice.novel.domain.entity.Novel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NovelDetailsDto {
	private Novel novel;
	private List<ChapterDto> chapters;

	public NovelDetailsDto(Novel novel, List<ChapterDto> chapters) {
		this.novel = novel;
		this.chapters = chapters;
	}

	public NovelDetailsDto() {
	}
}
