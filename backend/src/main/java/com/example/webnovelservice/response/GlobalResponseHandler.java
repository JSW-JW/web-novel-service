package com.example.webnovelservice.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.webnovelservice.exception.ResourceNotFoundException;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalResponseHandler extends ResponseEntityExceptionHandler {

	// @Override
	// public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
	// 	return true;
	// }
	//
	// @Override
	// public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
	// 	Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
	// 	ServerHttpResponse response) {
	// 	if (methodParameter.getContainingClass().isAnnotationPresent(RestController.class)) {
	// 		if (!Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(IgnoreResponseBinding.class)) {
	// 			if ((!(o instanceof ErrorResponse)) && (!(o instanceof SuccessResponse))) {
	// 				SuccessResponse<Object> responseBody = new SuccessResponse<>(o);
	// 				return responseBody;
	// 			}
	// 		}
	// 	}
	// 	return o;
	// }

	// handleHttpMediaTypeNotSupported : triggers when the JSON is invalid
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
		HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> details = new ArrayList<String>();

		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

		details.add(builder.toString());

		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Invalid JSON", details);

		return ResponseEntityBuilder.build(err);

	}

	// handleHttpMessageNotReadable : triggers when the JSON is malformed
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
		HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());

		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Malformed JSON request",
			details);

		return ResponseEntityBuilder.build(err);
	}

	// handleMethodArgumentNotValid : triggers when @Valid fails
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(error -> error.getObjectName() + " : " + error.getDefaultMessage())
			.collect(Collectors.toList());

		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Validation Errors",
			details);

		return ResponseEntityBuilder.build(err);
	}

	// handleMissingServletRequestParameter : triggers when there are missing parameters
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
		HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getParameterName() + " parameter is missing");

		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Missing Parameters",
			details);

		return ResponseEntityBuilder.build(err);
	}

	// handleMethodArgumentTypeMismatch : triggers when a parameter's type does not match
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
		WebRequest request) {
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());

		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Mismatch Type", details);

		return ResponseEntityBuilder.build(err);
	}

	// handleConstraintViolationException : triggers when @Validated fails
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(Exception ex, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());

		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Constraint Violation",
			details);

		return ResponseEntityBuilder.build(err);
	}

	// handleResourceNotFoundException : triggers when there is no resource with the specified ID in BDD
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());

		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND, "Resource Not Found", details);

		return ResponseEntityBuilder.build(err);
	}

	// handleNoHandlerFoundException : triggers when the handler method is invalid
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
		HttpStatusCode status, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));

		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Method Not Found", details);

		return ResponseEntityBuilder.build(err);
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getLocalizedMessage());

		ErrorResponse err = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Error occurred", details);

		return ResponseEntityBuilder.build(err);
	}
}
