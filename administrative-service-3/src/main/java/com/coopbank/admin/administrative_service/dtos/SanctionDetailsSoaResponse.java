package com.coopbank.admin.administrative_service.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SanctionDetailsSoaResponse {
	private String correlationID;
	private String elapsedTime;
	private String messageID;
	private String statusCode;
	private String statusDescription;
	private String statusDescriptionKey;
	private String applicationID;
	private String messageCode;
	private String messageDescription;
	private String messageType;
	private int numberOfMatches;
	private List<Person> persons;
	private List<Entity> entities;
}
