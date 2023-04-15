package com.example.webnovelservice.model.dto.response;

import lombok.Builder;

@Builder
public class PurchaseDto {
	private Long id;
	private Long userId;
	private Long chapterId;
}
