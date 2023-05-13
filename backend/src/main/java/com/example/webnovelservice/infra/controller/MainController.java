package com.example.webnovelservice.infra.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@GetMapping("/health")
	public String healthCheck(){
		return "It's running well :)";
	}
}
