package com.example.webnovelservice.payment.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePurchaseRequest {
	@NotNull
	private Long chapterId;

	public CreatePurchaseRequest(Long chapterId) {
		this.chapterId = chapterId;
	}
	public CreatePurchaseRequest() {
	}
}
