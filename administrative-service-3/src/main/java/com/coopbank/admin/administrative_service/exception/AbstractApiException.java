package com.coopbank.admin.administrative_service.exception;

import lombok.Getter;

@Getter
public abstract class AbstractApiException extends AbstractCustomException {
	private static final long serialVersionUID = -1968152500360941756L;
	private String uri;

	public AbstractApiException(int statusCode, String message, String uri) {
		super(statusCode, message);
		this.uri = uri;
	}

}
