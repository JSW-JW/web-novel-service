package com.example.webnovelservice.domain.payment;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webnovelservice.model.dto.request.CreateTokenTransactionRequest;
import com.example.webnovelservice.model.dto.response.TokenTransactionDto;
import com.example.webnovelservice.response.SuccessResponse;
import com.example.webnovelservice.security.CurrentUser;
import com.example.webnovelservice.security.UserPrincipal;

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
	public SuccessResponse<TokenTransactionDto> createTokenTransaction(@Valid @RequestBody CreateTokenTransactionRequest request,
		@CurrentUser UserPrincipal userPrincipal) {
		Long userId = userPrincipal.getId();

		TokenTransactionDto transactionDto = tokenTransactionService.chargeOwnershipToken(userId, request);
		return new SuccessResponse<>(transactionDto);
	}
}
