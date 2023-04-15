package com.example.webnovelservice.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreatePurchaseRequest {
	@NotNull
	private Long chapterId;
}
