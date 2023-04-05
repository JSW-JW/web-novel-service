package com.example.webnovelservice.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webnovelservice.model.command.PurchaseRequest;
import com.example.webnovelservice.model.dto.PurchaseDto;
import com.example.webnovelservice.model.entity.transaction.Purchase;
import com.example.webnovelservice.security.CurrentUser;
import com.example.webnovelservice.security.UserPrincipal;
import com.example.webnovelservice.service.PurchaseService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

	private final PurchaseService purchaseService;
	private final ModelMapper modelMapper;

	public PurchaseController(PurchaseService purchaseService, ModelMapper modelMapper) {
		this.purchaseService = purchaseService;
		this.modelMapper = modelMapper;
	}

	@PostMapping
	public ResponseEntity<PurchaseDto> purchaseChapter(HttpServletRequest request, @Valid @RequestBody PurchaseRequest purchaseRequest, @CurrentUser UserPrincipal userPrincipal) {
		Long userId = userPrincipal.getId();
		Purchase purchase = purchaseService.purchaseChapter(userId, purchaseRequest.getChapterId());
		PurchaseDto purchaseDto = modelMapper.map(purchase, PurchaseDto.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(purchaseDto);
	}
}
