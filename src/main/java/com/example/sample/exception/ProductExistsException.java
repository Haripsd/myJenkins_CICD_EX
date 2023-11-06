package com.example.sample.exception;

import org.springframework.http.HttpStatus;

public class ProductExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final HttpStatus status;
	
	public ProductExistsException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
}
