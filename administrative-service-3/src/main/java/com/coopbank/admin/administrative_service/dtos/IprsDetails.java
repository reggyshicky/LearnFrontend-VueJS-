package com.coopbank.admin.administrative_service.dtos;

import com.coopbank.admin.administrative_service.ws.client.customer.iprs.HumanInfoCommon;

public record IprsDetails(String firstName, String middleName, String lastName, 
		String gender, String dateOfBirth, String documentType, String documentNo, 
		String documentSerial, byte[] photo) {
	
	public IprsDetails(HumanInfoCommon details) {
		this(details.getFirstName() != null ? details.getFirstName().getValue() : null,
				details.getMiddleName() != null ? details.getMiddleName().getValue() : null,
				details.getLastName() != null ? details.getLastName().getValue() : null,
				details.getGender() != null ? details.getGender().getValue() : null,
				details.getDateOfBirth() != null ? details.getDateOfBirth().getValue() : null,
				details.getDocumentType() != null ? details.getDocumentType().getValue() : null,
				details.getDocumentNumber() != null ? details.getDocumentNumber().getValue() : null,
				details.getDocumentSerial() != null ? details.getDocumentSerial().getValue(): null,
				details.getPhoto() != null ? details.getPhoto().getValue(): null);
	}
}
