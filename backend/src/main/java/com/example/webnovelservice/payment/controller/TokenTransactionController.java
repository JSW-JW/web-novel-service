package com.example.webnovelservice.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webnovelservice.payment.dto.request.CreateTokenTransactionRequest;
import com.example.webnovelservice.payment.dto.response.TokenTransactionDto;
import com.example.webnovelservice.commons.annotation.CurrentUser;
import com.example.webnovelservice.commons.security.UserPrincipal;
import com.example.webnovelservice.payment.domain.service.OwnershipTokenTransactionService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/transactions")
public class TokenTransactionController {

	private final OwnershipTokenTransactionService tokenTransactionService;

	public TokenTransactionController(OwnershipTokenTransactionService tokenTransactionService) {
		this.tokenTransactionService = tokenTransactionService;
	}

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<TokenTransactionDto> createTokenTransaction(@Valid @RequestBody CreateTokenTransactionRequest request,
		@CurrentUser UserPrincipal userPrincipal) {
		Long userId = userPrincipal.getId();

		TokenTransactionDto transactionDto = tokenTransactionService.chargeOwnershipToken(userId, request);
		return ResponseEntity.ok(transactionDto);
	}
}
