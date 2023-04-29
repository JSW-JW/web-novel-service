package com.example.webnovelservice.domain.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webnovelservice.model.dto.request.CreatePurchaseRequest;
import com.example.webnovelservice.model.dto.response.ChapterDto;
import com.example.webnovelservice.model.dto.response.PurchaseDto;
import com.example.webnovelservice.security.CurrentUser;
import com.example.webnovelservice.security.UserPrincipal;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseController {

	private final PurchaseService purchaseService;

	public PurchaseController(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<PurchaseDto> purchaseChapter(@Valid @RequestBody CreatePurchaseRequest createPurchaseRequest,
		@CurrentUser UserPrincipal userPrincipal) {
		Long userId = userPrincipal.getId();
		PurchaseDto purchaseDto = purchaseService.purchaseChapter(userId, createPurchaseRequest.getChapterId());
		return ResponseEntity.ok(purchaseDto);
	}
}
