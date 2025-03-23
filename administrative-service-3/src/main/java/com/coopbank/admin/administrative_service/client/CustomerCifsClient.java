//package com.coopbank.admin.administrative_service.client;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import org.apache.hc.core5.http.HttpStatus;
//import org.springframework.ws.client.core.WebServiceMessageCallback;
//import org.springframework.ws.client.core.WebServiceTemplate;
//import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
//import org.springframework.ws.client.support.interceptor.ClientInterceptor;
//import org.springframework.ws.soap.SoapHeader;
//import org.springframework.ws.soap.SoapMessage;
//
//import jakarta.xml.bind.JAXBContext;
//import jakarta.xml.bind.JAXBElement;
//import jakarta.xml.bind.Marshaller;
//import com.coopbank.admin.administrative_service.exception.CustomerCifApiException;
//import com.coopbank.admin.administrative_service.util.CalendarUtil;
//import com.coopbank.admin.administrative_service.util.SoaClientUtil;
//import ke.co.coopbank.bidbond.engine.ws.client.customer.id.CredentialsType;
//import ke.co.coopbank.bidbond.engine.ws.client.customer.id.CustomerIDRqType;
//import ke.co.coopbank.bidbond.engine.ws.client.customer.id.CustomerIDRsType;
//import ke.co.coopbank.bidbond.engine.ws.client.customer.id.CustomerIDRsType.CustomerIDDetails;
//import ke.co.coopbank.bidbond.engine.ws.client.customer.id.ObjectFactory;
//import ke.co.coopbank.bidbond.engine.ws.client.customer.id.RequestHeaderType;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//
//@Log4j2
//@Getter
//@RequiredArgsConstructor
//public class CustomerCifsClient extends WebServiceGatewaySupport {
//	private final String endpoint;
//	private final String systemCode;
//	private final String soapCallback;
//
//	public enum CustomerIdIdentificationType {
//		NATID, PSPRT, ALIENID;
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<CustomerIDDetails> getCustomerCifs(CustomerIdIdentificationType identificationType, String identificationNumber) {
//		RequestHeaderType header = createRequestHeader();
//
//		WebServiceTemplate template = getWebServiceTemplate();
//		Map<String, String> headers = new HashMap<>();
//
//		template.setInterceptors(new ClientInterceptor[] { SoaClientUtil.intercept(headers, log) });
//
//		CustomerIDRqType requestBody = new CustomerIDRqType();
//		requestBody.setUniqueIdentifierType(identificationType.name());
//		requestBody.setUniqueIdentifierValue(identificationNumber);
//
//		Object response = null;
//		try {
//			response = template.marshalSendAndReceive(endpoint, new ObjectFactory().createCustomerIDRq(requestBody),
//					getRequestCallback(header));
//		} catch (Exception e) {
//			log.error("CustomerBlacklistApiException Error on marshalling/unmarshalling: ", e);
//			throw new CustomerCifApiException(HttpStatus.SC_SERVICE_UNAVAILABLE, "Service unavailable try again later", endpoint);
//		}
//
//		if (response == null || !headers.containsKey("head:StatusCode")) {
//			throw new CustomerCifApiException(HttpStatus.SC_SERVICE_UNAVAILABLE, "Service unavailable try again later", endpoint);
//		}
//		var statusCode = headers.get("head:StatusCode");
//
//		if (!statusCode.equalsIgnoreCase("S_000") && !statusCode.equalsIgnoreCase("S_001")) {
//			throw new CustomerCifApiException(HttpStatus.SC_SERVICE_UNAVAILABLE, "Service unavailable try again later", endpoint);
//		}
//
//		if (statusCode.equalsIgnoreCase("S_000")) {
//			if(!headers.containsKey("head:StatusMessages.head:MessageDescription")) {
//				throw new CustomerCifApiException(HttpStatus.SC_SERVICE_UNAVAILABLE, "Service unavailable try again later", endpoint);
//			}
//
//			 var message = headers.get("head:StatusMessages.head:MessageDescription");
//
//			 if(message.equalsIgnoreCase("Invalid Document Type"))
//				 throw new CustomerCifApiException(HttpStatus.SC_BAD_REQUEST, "Document type is invalid", endpoint);
//			 if(message.equalsIgnoreCase("Pls enter Document ID Number"))
//					throw new CustomerCifApiException(HttpStatus.SC_BAD_REQUEST, "Document id number is required", endpoint);
//			 if(message.equalsIgnoreCase("No inputs provided"))
//					throw new CustomerCifApiException(HttpStatus.SC_BAD_REQUEST, "Check your request. Something could be missing", endpoint);
//			 if(message != null && !message.trim().equalsIgnoreCase("No details fetched for the given inputs"))
//					throw new CustomerCifApiException(HttpStatus.SC_SERVICE_UNAVAILABLE, "Service unavailable try again later", endpoint);
//
//			return Collections.EMPTY_LIST;
//		}
//
//		// S_001
//		JAXBElement<CustomerIDRsType> typeCastResponse = (JAXBElement<CustomerIDRsType>) response;
//
//		var idrsType = typeCastResponse.getValue();
//
//		return idrsType.getCustomerIDDetails();
//	}
//
//	private RequestHeaderType createRequestHeader() {
//		RequestHeaderType requestHeader = new RequestHeaderType();
//		requestHeader.setCreationTimestamp(CalendarUtil.convert(LocalDateTime.now()));
//		CredentialsType credentials = new CredentialsType();
//		credentials.setSystemCode(new ObjectFactory().createCredentialsTypeSystemCode(systemCode));
//		credentials.setBankID(new ObjectFactory().createBankID(""));
//		requestHeader.setCredentials(credentials);
//		String randomUUID = UUID.randomUUID().toString();
//		requestHeader.setMessageID(randomUUID);
//		return requestHeader;
//	}
//
//	private WebServiceMessageCallback getRequestCallback(RequestHeaderType header) {
//		return message -> {
//			try {
//				((SoapMessage) message).setSoapAction(soapCallback);
//				// get the header from the SOAP message
//				SoapHeader soapHeader = ((SoapMessage) message).getSoapHeader();
//				// create a marshaller
//				JAXBContext context = JAXBContext.newInstance(RequestHeaderType.class);
//				Marshaller marshaller = context.createMarshaller();
//				// marshal the headers into the specified result
//				marshaller.marshal(new ObjectFactory().createRequestHeader(header), soapHeader.getResult());
//			} catch (Exception e) {
//				e.printStackTrace();
//				log.error("error during marshalling of the SOAP headers", e);
//			}
//		};
//	}
//}
