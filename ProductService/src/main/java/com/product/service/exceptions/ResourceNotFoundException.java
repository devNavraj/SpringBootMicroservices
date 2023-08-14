package com.product.service.exceptions;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {

	private String errorCode;
	
	public ResourceNotFoundException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}
