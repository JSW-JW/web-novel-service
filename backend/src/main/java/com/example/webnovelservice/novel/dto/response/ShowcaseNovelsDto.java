package com.example.webnovelservice.novel.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowcaseNovelsDto {
	private String title;
	private List<NovelDto> novels;
}
