package com.example.webnovelservice.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTokenTransactionRequest {
	private Long novelId;
	private Integer tokensToCharge;
}
