package com.example.webnovelservice.commons.response.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnauthorizedUriException extends RuntimeException {
    public UnauthorizedUriException(String message) {
        super(message);
    }

    public UnauthorizedUriException(String message, Throwable cause) {
        super(message, cause);
    }
}
