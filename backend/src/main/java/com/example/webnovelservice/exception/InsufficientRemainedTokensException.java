package com.example.webnovelservice.exception;

public class InsufficientRemainedTokensException extends RuntimeException {
	public InsufficientRemainedTokensException() {
		super("Insufficient novel tokens.");
	}
}
