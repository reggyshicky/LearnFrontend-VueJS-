package com.coopbank.admin.administrative_service.exception;

public class CustomerCifApiException extends AbstractApiException {
	private static final long serialVersionUID = 4311901712447951896L;
	
	public CustomerCifApiException(int statusCode, String message, String uri) {
		super(statusCode, message, uri);
	}

}
