package com.example.webnovelservice.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenTransactionDto {
	private Long id;
	private Integer tokensPurchased;
	private Integer price = -1;
}
