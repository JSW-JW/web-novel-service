package com.example.webnovelservice.payload.novel;


import java.util.List;

import com.example.webnovelservice.model.entity.novel.Chapter;
import com.example.webnovelservice.model.entity.novel.Novel;

import lombok.Builder;

@Builder
public class NovelDetailsResponse {
	private Novel novel;
	private List<Chapter> chapters;
}
