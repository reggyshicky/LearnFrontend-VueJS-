package com.coopbank.admin.administrative_service.client;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import com.coopbank.admin.administrative_service.SoaRequestRestTemplateGatewaySupport;
import com.coopbank.admin.administrative_service.config.SoaPropertiesConfig.CustomerSanctionDetailsProperties;
import com.coopbank.admin.administrative_service.dtos.SanctionDetails;
import com.coopbank.admin.administrative_service.dtos.SanctionDetailsSoaRequest;
import com.coopbank.admin.administrative_service.dtos.SanctionDetailsSoaResponse;
import com.coopbank.admin.administrative_service.exception.CustomerSanctionDetailsApiUnavailableException;
import com.coopbank.admin.administrative_service.util.CalendarUtil;
import com.coopbank.admin.administrative_service.util.SanctionDetailsParser;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
public class CustomerSanctionDetailsClient extends SoaRequestRestTemplateGatewaySupport {
	private final String endpoint;
	private final String systemCode;
	private final String soapCallback;
	private final String templatePath;
	
	public enum CustomerSanctionDetailsIdentificationType {
		// TODO - REQUEST EA, PS: THE FOLLOWING ARE JUST QUESSES
		NATIID, CORP_REG_ID, PSPRT, ALIENID;
	}
	
	public CustomerSanctionDetailsClient(CustomerSanctionDetailsProperties sanctionDetails, RestTemplate restTemplate) {
		super(restTemplate, sanctionDetails.getUsername(), sanctionDetails.getPassword(),
				sanctionDetails.getSoapCallback(), sanctionDetails.getEndpoint());
		this.endpoint = sanctionDetails.getEndpoint();
		this.systemCode = sanctionDetails.getSystemCode();
		this.soapCallback = sanctionDetails.getSoapCallback();
		this.templatePath = sanctionDetails.getTemplate();
	}

	public Optional<SanctionDetailsSoaResponse> getSanctionDetails(SanctionDetails sanctionDetails) {

		TemplateLoader loader = new ClassPathTemplateLoader("/templates");
		Handlebars handlebars = new Handlebars(loader);
		Template template;

		try {
			template = handlebars.compile(templatePath);
		} catch (IOException e) {
			var message = "IOException occurred while compiling handle bars template";
			log.error("{}: {}", message, e);
			throw new RuntimeException(message);
		}

		var id = UUID.randomUUID().toString();
		SanctionDetailsSoaRequest enhancedSanctionDetails = new SanctionDetailsSoaRequest(sanctionDetails,
				CalendarUtil.convert(LocalDateTime.now()).toString(), id, id, systemCode);
		System.out.println(enhancedSanctionDetails);
		
		String request = null;
		try {
			request = template.apply(enhancedSanctionDetails);
		} catch (IOException e) {
			var message = "IOException occurred while applying data to handle bars template";
			log.error("{}: {}", message, e);
			throw new RuntimeException(message);
		}

		ResponseEntity<String> responseEntity = sendSoaRequest(request);

		if (responseEntity.getStatusCode().is5xxServerError() || responseEntity.getStatusCode().is3xxRedirection() ||
				!responseEntity.hasBody()) {
			// TODO - include in global advice
			throw new CustomerSanctionDetailsApiUnavailableException(HttpStatus.SC_SERVICE_UNAVAILABLE,
					"Service unavailable please try again later!");
		}
		log.info("Response Entity: {}", responseEntity.toString());

		if (responseEntity.getStatusCode().is4xxClientError()) {
			throw new CustomerSanctionDetailsApiUnavailableException(responseEntity.getStatusCode().value(),
					"Check request!");
		}
		
		var xmlSerializedContentResponse = responseEntity.getBody();
		log.info("Response: {}", xmlSerializedContentResponse);

		SanctionDetailsParser parser = new SanctionDetailsParser();
		SanctionDetailsSoaResponse response = parser.parse(xmlSerializedContentResponse);
		
		if(!response.getStatusCode().equalsIgnoreCase("S_001")) {
			return Optional.empty();
		}

		return Optional.of(response);
	}
}
