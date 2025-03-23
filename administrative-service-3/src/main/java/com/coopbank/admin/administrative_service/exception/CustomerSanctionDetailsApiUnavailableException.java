package com.coopbank.admin.administrative_service.exception;

public class CustomerSanctionDetailsApiUnavailableException extends AbstractCustomException {
	private static final long serialVersionUID = -3891056658794524524L;

	public CustomerSanctionDetailsApiUnavailableException(int statusCode, String message) {
		super(statusCode, message);
	}
}
