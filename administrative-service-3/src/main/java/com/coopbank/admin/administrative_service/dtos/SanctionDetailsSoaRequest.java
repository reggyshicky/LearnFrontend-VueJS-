package com.coopbank.admin.administrative_service.dtos;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SanctionDetailsSoaRequest extends BaseSoaRequest implements Serializable {
	private static final long serialVersionUID = -8253108822772138265L;

	private String minMatchScore;
	private String customerType;
	private String fullName;
	private String identificationDocType;
	private String identificationDocNo;
	private String nationality;
	private String dateOfBirth;
	private String countryOfBirth;
	private String addressProvince;
	private String postalAddress;
	private String postalCodeAddress;
	private String addressCountry;

	public SanctionDetailsSoaRequest(SanctionDetails details, String creationTimestamp, String correlationId,
			String messageId, String systemCode) {
		super(creationTimestamp, correlationId, messageId, systemCode);
		this.minMatchScore = details.getMinMatchScore();
//		this.creationTimestamp = creationTimestamp;
//		this.correlationId = correlationId;
//		this.messageId = messageId;
//		this.systemCode = systemCode;
	}


}
