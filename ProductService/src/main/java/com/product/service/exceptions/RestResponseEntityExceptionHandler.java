package com.product.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.product.service.models.ErrorResponse;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleProductServiceException(ResourceNotFoundException ex) {
		return new ResponseEntity<ErrorResponse>(new ErrorResponse()
				.builder()
				.errorMessage(ex.getMessage())
				.errorCode(ex.getErrorCode())
				.build(), HttpStatus.NOT_FOUND);
	}
}
