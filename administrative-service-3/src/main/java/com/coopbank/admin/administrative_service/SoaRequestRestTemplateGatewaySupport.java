package com.coopbank.admin.administrative_service;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Base64;

@Log4j2
@RequiredArgsConstructor
public class SoaRequestRestTemplateGatewaySupport {
	private final RestTemplate restTemplate;
	private final String soaUsername, soaPassword, soapAction, endpoint;
	
	public ResponseEntity<String> sendSoaRequest(String request) {
		try {
			log.info("Request: {}", request);

			HttpHeaders headers = new HttpHeaders();

			String encodedAuth = "Basic "
					+ Base64.getEncoder().encodeToString(String.format("%s:%s", soaUsername, soaPassword).getBytes());

			headers.add("Authorization", encodedAuth);
			headers.add("SOAPAction", "\""+soapAction+ "\""
					);
			headers.setContentType(MediaType.TEXT_XML);

			HttpEntity<String> sms = new HttpEntity<String>(request, headers);

			System.out.println("headers:::"+headers+" endpoint"+endpoint);

			return restTemplate.exchange(endpoint, HttpMethod.POST, sms, String.class);

		} catch (HttpClientErrorException | HttpServerErrorException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
		} catch (Exception e) {

			log.error("Error while sending soa request for soapAction {}  endpoint {}", soapAction, endpoint, e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error occured while calling  :" + e.getMessage());
		}
	}
}
