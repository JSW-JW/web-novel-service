package com.example.webnovelservice.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RegisteredUserException extends RuntimeException {
	private String resourceName;
	private String fieldName;
	private Object fieldValue;

	public RegisteredUserException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	public RegisteredUserException(String message) {
		super(message);
	}
}
