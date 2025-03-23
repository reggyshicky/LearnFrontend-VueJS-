package com.coopbank.admin.administrative_service.dtos;

import java.io.Serializable;

import com.coopbank.admin.administrative_service.client.CustomerSanctionDetailsClient.CustomerSanctionDetailsIdentificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SanctionDetails implements Serializable {
	private static final long serialVersionUID = -3859611277009576323L;
	private String minMatchScore;
	private String identificationDocNo;
}
