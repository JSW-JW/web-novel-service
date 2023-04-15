package com.example.webnovelservice.model.dto.response;


import java.util.List;

import com.example.webnovelservice.domain.novel.entity.Chapter;
import com.example.webnovelservice.domain.novel.entity.Novel;

import lombok.Builder;

@Builder
public class NovelDetailsResponse {
	private Novel novel;
	private List<Chapter> chapters;
}
