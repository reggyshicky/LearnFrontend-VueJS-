package com.coopbank.admin.administrative_service;

import com.coopbank.admin.administrative_service.client.IPRSClient;
import com.coopbank.admin.administrative_service.config.IPRSConfig;
import com.coopbank.admin.administrative_service.config.SoaPropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdministrativeServiceApplication {

	public static void main(String[] args) {

		var context = SpringApplication.run(AdministrativeServiceApplication.class, args);
		var soaProperties = (IPRSClient) context.getBean(IPRSClient.class);
		System.out.println(soaProperties);
	}

}
