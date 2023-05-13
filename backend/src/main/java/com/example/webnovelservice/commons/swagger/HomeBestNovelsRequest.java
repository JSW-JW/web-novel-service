package com.example.webnovelservice.commons.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class HomeBestNovelsRequest {

	@Schema(description = "List of showcase type IDs", example = "[1, 2]")
	private List<Long> showcaseTypeIds;

	public List<Long> getShowcaseTypeIds() {
		return showcaseTypeIds;
	}

	public void setShowcaseTypeIds(List<Long> showcaseTypeIds) {
		this.showcaseTypeIds = showcaseTypeIds;
	}
}
