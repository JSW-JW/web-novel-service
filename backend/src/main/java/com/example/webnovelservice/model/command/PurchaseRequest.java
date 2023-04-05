package com.example.webnovelservice.model.command;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PurchaseRequest {
	@NotNull
	private Long chapterId;
}
