package com.coopbank.admin.administrative_service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomerIprsIdentificationType {

	NATID("1"), PASSPORT("2"), ALIENID("3");

	private final String id;
}
