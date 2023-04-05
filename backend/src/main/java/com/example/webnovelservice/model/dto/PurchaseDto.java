package com.example.webnovelservice.model.dto;

import lombok.Builder;

@Builder
public class PurchaseDto {
	private Long id;
	private Long userId;
	private Long chapterId;
}
