package com.example.webnovelservice.payment.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenTransactionDto {
	private Long id;
	private Integer tokensPurchased;
	private Integer price = -1;

	public TokenTransactionDto(Long id, Integer tokensPurchased, Integer price) {
		this.id = id;
		this.tokensPurchased = tokensPurchased;
		this.price = price;
	}

	public TokenTransactionDto() {
	}
}
