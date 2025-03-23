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
public class EligibilityRequest implements Serializable {
	private static final long serialVersionUID = -5466327036075601933L;
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String fullName;
	private String phoneNumber;
	private String emailAddress;
}
