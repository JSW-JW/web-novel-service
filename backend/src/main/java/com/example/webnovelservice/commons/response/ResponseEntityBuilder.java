package com.example.webnovelservice.commons.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder {

	public static ResponseEntity<Object> build(ErrorResponse errorResponse) {
		return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
	}

	public static ResponseEntity<Object> build(String message, HttpStatus status, Object responseObject) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("status", status);
		response.put("data", responseObject);

		return new ResponseEntity<>(response, status);
	}

}
