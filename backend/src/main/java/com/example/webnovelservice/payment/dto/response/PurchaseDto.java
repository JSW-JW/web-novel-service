package com.example.webnovelservice.payment.dto.response;

import com.example.webnovelservice.chapter.dto.response.ChapterDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto {
	private Long id;
	private ChapterDto chapter;
	private Integer tokensLeft;
}
