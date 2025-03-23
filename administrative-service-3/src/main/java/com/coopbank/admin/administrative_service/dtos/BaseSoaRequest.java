package com.coopbank.admin.administrative_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseSoaRequest {
	private String creationTimestamp;
	private String correlationId;
	private String messageId;
	private String systemCode;
}
