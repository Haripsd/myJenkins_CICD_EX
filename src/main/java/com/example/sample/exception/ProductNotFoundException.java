package com.example.sample.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private final HttpStatus status;
	
	public ProductNotFoundException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

}
