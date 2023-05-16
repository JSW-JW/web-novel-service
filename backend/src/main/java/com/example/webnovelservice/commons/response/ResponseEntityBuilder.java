package com.example.webnovelservice.commons.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder {
	public static ResponseEntity<Object> success(HttpStatus status, Object responseObject) {
		Map<String, Object> response = new HashMap<>();
		response.put("data", responseObject);

		return new ResponseEntity<>(response, status);
	}

	public static ResponseEntity<Object> error(ErrorState errorState, Object responseObject) {
		Map<String, Object> response = new HashMap<>();
		response.put("status_code", errorState.getHttpStatus().value());
		response.put("error_code", errorState.getErrorCode());
		response.put("error", responseObject);

		return new ResponseEntity<>(response, errorState.getHttpStatus());
	}

}
