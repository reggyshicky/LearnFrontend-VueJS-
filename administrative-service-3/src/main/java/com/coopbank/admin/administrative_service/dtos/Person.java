package com.coopbank.admin.administrative_service.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Person {
	private String identificationDocNo;
	private String identificationDocType;
	private String name;
	private String address;
	private String nationality;
	private String dateofBirth;
	private String countryofBirth;
	private String listedBy;
	private String matchScore;

}
