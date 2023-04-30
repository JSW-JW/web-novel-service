package com.example.webnovelservice.commons.response.exception;

public class InsufficientRemainedTokensException extends RuntimeException {
	public InsufficientRemainedTokensException() {
		super("Insufficient novel tokens.");
	}
}
