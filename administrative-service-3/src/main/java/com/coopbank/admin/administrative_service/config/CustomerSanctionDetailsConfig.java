package com.coopbank.admin.administrative_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.coopbank.admin.administrative_service.client.CustomerSanctionDetailsClient;

@Configuration
public class CustomerSanctionDetailsConfig {
	private final SoaPropertiesConfig config;
	private final RestTemplate restTemplate;
	
	public CustomerSanctionDetailsConfig(SoaPropertiesConfig config, RestTemplate restTemplate) {
		this.config = config;
		this.restTemplate = restTemplate;
	}
	
	@Bean
	public CustomerSanctionDetailsClient sanctionDetailsClient() {
		CustomerSanctionDetailsClient client = new CustomerSanctionDetailsClient(config.getSanctionDetails(), restTemplate);
		return client;
	}
}
