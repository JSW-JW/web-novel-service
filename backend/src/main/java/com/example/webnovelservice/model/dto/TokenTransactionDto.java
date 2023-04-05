package com.example.webnovelservice.model.dto;

import lombok.Builder;

@Builder
public class TokenTransactionDto {
	private Long id;
	private Long userId;
	private int tokensPurchased;
}
