package com.example.webnovelservice.payment.dto.response;

import com.example.webnovelservice.chapter.dto.response.ChapterDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseDto {
	private Long id;
	private ChapterDto chapter;
	private Integer tokensLeft;

	public PurchaseDto() {
	}

	public PurchaseDto(Long id, ChapterDto chapter, Integer tokensLeft) {
		this.id = id;
		this.tokensLeft = tokensLeft;
		this.chapter = chapter;
	}
}
