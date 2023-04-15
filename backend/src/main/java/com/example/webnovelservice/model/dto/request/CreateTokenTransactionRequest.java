package com.example.webnovelservice.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTokenTransactionRequest {
	private Long novelId;
	private Integer tokensToCharge;

	public CreateTokenTransactionRequest(Long novelId, Integer tokensToCharge) {
		this.novelId = novelId;
		this.tokensToCharge = tokensToCharge;
	}

	public CreateTokenTransactionRequest() {
	}
}
