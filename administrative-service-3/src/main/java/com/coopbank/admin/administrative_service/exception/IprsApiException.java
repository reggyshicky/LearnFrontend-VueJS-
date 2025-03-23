package com.coopbank.admin.administrative_service.exception;

public class IprsApiException extends AbstractApiException {
	private static final long serialVersionUID = 6461335615341344048L;

	public IprsApiException(int statusCode, String message, String uri) {
		super(statusCode, message, uri);
	}
}
