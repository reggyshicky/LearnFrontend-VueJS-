package com.coopbank.admin.administrative_service.exception;

import lombok.Getter;

@Getter
public abstract class AbstractCustomException extends RuntimeException {
	private static final long serialVersionUID = 1880163911893571333L;
	
	private Integer statusCode;
	
	public AbstractCustomException(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}

}
