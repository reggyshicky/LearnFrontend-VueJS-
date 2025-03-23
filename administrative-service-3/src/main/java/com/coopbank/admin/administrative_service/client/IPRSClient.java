package com.coopbank.admin.administrative_service.client;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import lombok.ToString;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;
import com.coopbank.admin.administrative_service.CustomerIprsIdentificationType;
import com.coopbank.admin.administrative_service.dtos.IprsDetails;
import com.coopbank.admin.administrative_service.exception.IprsApiException;
import com.coopbank.admin.administrative_service.util.CalendarUtil;
import com.coopbank.admin.administrative_service.util.SoaClientUtil;
import com.coopbank.admin.administrative_service.ws.client.customer.iprs.ObjectFactory;
import com.coopbank.admin.administrative_service.ws.client.customer.iprs.CredentialsType;
import com.coopbank.admin.administrative_service.ws.client.customer.iprs.GetIPRSDataByGenericID;
import com.coopbank.admin.administrative_service.ws.client.customer.iprs.HumanInfoCommon;
import com.coopbank.admin.administrative_service.ws.client.customer.iprs.RequestHeaderType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@RequiredArgsConstructor
@ToString
public class IPRSClient extends WebServiceGatewaySupport {
	private final String endpoint;
	private final String systemCode;
	private final String soapCallback;
	private final String clientUsername;
	private final String clientPassword;

	@SuppressWarnings("unchecked")
	public Optional<IprsDetails> getIprsDetails(CustomerIprsIdentificationType type, String idNo) {
		RequestHeaderType header = createRequestHeader(systemCode, UUID.randomUUID().toString(), LocalDateTime.now());

		WebServiceTemplate template = getWebServiceTemplate();
		Map<String, String> headers = new HashMap<>();

		template.setInterceptors(new ClientInterceptor[] { SoaClientUtil.intercept(headers, log) });

		GetIPRSDataByGenericID body = new GetIPRSDataByGenericID();
		body.setPass(new ObjectFactory().createGetIPRSDataByGenericIDPass(clientPassword));
		body.setLog(new ObjectFactory().createGetIPRSDataByGenericIDLog(clientUsername));
		body.setDocType(new ObjectFactory().createGetIPRSDataByGenericIDDocType(type.getId()));
		body.setDocNumber(new ObjectFactory().createGetIPRSDataByGenericIDDocNumber(idNo));

		Object response = null;
		try {
			System.out.println("bodddddy" + body);
			System.out.println("header" + header);

			response = template.marshalSendAndReceive(endpoint,
					new ObjectFactory().createGetCitizensDetailsReqData(body), getRequestCallback(header));
		} catch (Exception e) {
			log.error("IPRSClient Error on marshalling/unmarshalling: ", e);
			throw new IprsApiException(HttpStatus.SC_SERVICE_UNAVAILABLE,
					"Service unavailable try again later", endpoint);
		}

		System.out.println(headers);
		if (response == null || !headers.containsKey("head:StatusMessages.head:MessageDescription")) {
			throw new IprsApiException(HttpStatus.SC_SERVICE_UNAVAILABLE,
					"Service unavailable try again later", endpoint);
		}

		var statusCode = headers.get("head:StatusMessages.head:MessageDescription");
//		var message = headers.get("head:StatusMessages.head:MessageDescription");

		if (!statusCode.equalsIgnoreCase("Success")) {
			throw new IprsApiException(HttpStatus.SC_BAD_REQUEST,
					statusCode, endpoint);
		}

//		if(statusCode.equalsIgnoreCase("S_001") && !message.equalsIgnoreCase("Success")) {
//			return Optional.empty();
//		}
		
		JAXBElement<HumanInfoCommon> typeCastResponse = (JAXBElement<HumanInfoCommon>) response;

		return Optional.of(new IprsDetails(typeCastResponse.getValue()));

	}

	private RequestHeaderType createRequestHeader(String systemCode, String messageId, LocalDateTime creationDateTime) {
		RequestHeaderType requestHeader = new RequestHeaderType();
		requestHeader.setCreationTimestamp(CalendarUtil.convert(creationDateTime));
		CredentialsType credentials = new CredentialsType();
		credentials.setSystemCode(new ObjectFactory().createCredentialsTypeSystemCode(systemCode));
		requestHeader.setCredentials(credentials);
		requestHeader.setMessageID(messageId);
		return requestHeader;
	}

	private WebServiceMessageCallback getRequestCallback(RequestHeaderType header) {
		return message -> {
			try {
				((SoapMessage) message).setSoapAction(soapCallback);
				// get the header from the SOAP message
				SoapHeader soapHeader = ((SoapMessage) message).getSoapHeader();
				// create a marshaller
				JAXBContext context = JAXBContext.newInstance(RequestHeaderType.class);
				Marshaller marshaller = context.createMarshaller();
				// marshal the headers into the specified result
				marshaller.marshal(new ObjectFactory().createRequestHeader(header), soapHeader.getResult());
			} catch (Exception e) {
				e.printStackTrace();
				log.error("error during marshalling of the SOAP headers", e);
			}
		};
	}
}
