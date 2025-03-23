package com.coopbank.admin.administrative_service.dtos;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SanctionDetailsResponse {
	private Boolean isIndividual;
	private String identificationType;
	private String identificationNo;
	private String fullName;
	private String address;
	private String nationality;
	private LocalDate dateOfBirth;
	private String countryOfBirth;
	private String listedBy;
	private String matchScore;

}
