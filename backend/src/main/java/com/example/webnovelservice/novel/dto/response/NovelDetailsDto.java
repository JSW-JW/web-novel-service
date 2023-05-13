package com.example.webnovelservice.novel.dto.response;


import java.util.List;

import com.example.webnovelservice.chapter.dto.response.ChapterDto;
import com.example.webnovelservice.novel.domain.entity.Novel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NovelDetailsDto {
	private NovelDto novel;
	private List<ChapterDto> chapters;
}
