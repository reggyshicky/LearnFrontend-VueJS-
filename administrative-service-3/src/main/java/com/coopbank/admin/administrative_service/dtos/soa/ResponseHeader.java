package com.coopbank.admin.administrative_service.dtos.soa;

import lombok.Data;

@Data
public class ResponseHeader {
    private String messageId;
    private String statusCode;
    private String statusDescription;
    private String messageCode;
    private String messageDescription;
}