package com.example.webnovelservice.commons.response;

import org.springframework.http.HttpStatus;

public enum ErrorState {
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "C_002"),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C_003"),
	FORBIDDEN(HttpStatus.FORBIDDEN, "C_004"),
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "C_005"),
	MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "C_006"),
	MAX_FILE_SIZE(HttpStatus.BAD_REQUEST, "C_007"),
	PARAMETER_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "C_008"),
	FAILED_VALIDATION(HttpStatus.BAD_REQUEST, "C_009"),
	METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "C_010"),
	NO_HANDLER_FOUND(HttpStatus.BAD_REQUEST, "C_011"),
	COMMON_EXCEPTION(HttpStatus.BAD_REQUEST, "C_012"),
	HTTP_MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "C_013"),
	MEDIA_TYPE_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "C_014"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "B_001");

	private final HttpStatus httpStatus;
	private final String errorCode;

	ErrorState(HttpStatus httpStatus, String errorCode) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
