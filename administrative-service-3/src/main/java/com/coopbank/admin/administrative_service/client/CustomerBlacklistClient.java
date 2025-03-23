//package com.coopbank.admin.administrative_service.client;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
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
//import com.coopbank.admin.administrative_service.exception.CustomerBlacklistApiException;
//import com.coopbank.admin.administrative_service.util.CalendarUtil;
//import com.coopbank.admin.administrative_service.util.SoaClientUtil;
//import com.coopbank.admin.administrative_service.ws.client.customer.blacklist.RequestHeaderType;
//import com.coopbank.admin.administrative_service.ws.client.customer.blacklist.BlacklistDetailsRqType;
//import com.coopbank.admin.administrative_service.ws.client.customer.blacklist.BlacklistDetailsRsType;
//import com.coopbank.admin.administrative_service.ws.client.customer.blacklist.CredentialsType;
//import com.coopbank.admin.administrative_service.ws.client.customer.blacklist.ObjectFactory;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//
//@Log4j2
//@Getter
//@RequiredArgsConstructor
//public class CustomerBlacklistClient extends WebServiceGatewaySupport {
//	private final String endpoint;
//	private final String systemCode;
//	private final String soapCallback;
//
//	@Getter
//	@RequiredArgsConstructor
//	public enum CustomerBlacklistIdentificationType {
//		ACCOUNT_NUMBER("AccountNumber"), CIF_NUMBER("CIFNumber"), ID_DOC_NUMBER("IDDocNumber");
//
//		private final String type;
//	}
//
//	@SuppressWarnings("unchecked")
//	public Optional<BlacklistDetailsRsType> getBlacklistDetails(CustomerBlacklistIdentificationType idType,
//			String idNo) {
//		RequestHeaderType header = createRequestHeader(systemCode, UUID.randomUUID().toString(), LocalDateTime.now());
//
//		WebServiceTemplate template = getWebServiceTemplate();
//		Map<String, String> headers = new HashMap<>();
//
//		template.setInterceptors(new ClientInterceptor[] { SoaClientUtil.intercept(headers, log) });
//
//		BlacklistDetailsRqType body = new BlacklistDetailsRqType();
//		body.setIdentificationType(idType.getType());
//		body.setIdentificationNumber(idNo);
//
//		Object response = null;
//		try {
//			response = template.marshalSendAndReceive(endpoint, new ObjectFactory().createBlacklistDetailsRq(body),
//					getRequestCallback(header));
//		} catch (Exception e) {
//			log.error("CustomerBlacklistApiException Error on marshalling/unmarshalling: ", e);
//			throw new CustomerBlacklistApiException(HttpStatus.SC_SERVICE_UNAVAILABLE,
//					"Service unavailable try again later", endpoint);
//		}
//
//		if (response == null || !headers.containsKey("head:StatusCode")) {
//			// TODO - handle in global advice
//			throw new CustomerBlacklistApiException(HttpStatus.SC_SERVICE_UNAVAILABLE, "Service unavailable try again later", endpoint);
//		}
//
//		var statusCode = headers.get("head:StatusCode");
//
//		if (statusCode.equalsIgnoreCase("E_000")) {
//			log.error("E_000");
//			throw new CustomerBlacklistApiException(HttpStatus.SC_SERVICE_UNAVAILABLE,
//							"Something happened, check your request and try again later", endpoint);
//		}
//
//		if (statusCode.equalsIgnoreCase("S_000")) {
//			return Optional.empty();
//		}
//
//		JAXBElement<BlacklistDetailsRsType> typeCastResponse = (JAXBElement<BlacklistDetailsRsType>) response;
//
//		return Optional.of(typeCastResponse.getValue());
//	}
//
//	private RequestHeaderType createRequestHeader(String systemCode, String messageId, LocalDateTime creationDateTime) {
//		RequestHeaderType requestHeader = new RequestHeaderType();
//		requestHeader.setCreationTimestamp(CalendarUtil.convert(creationDateTime));
//		CredentialsType credentials = new CredentialsType();
//		credentials.setSystemCode(new ObjectFactory().createCredentialsTypeSystemCode(systemCode));
//		requestHeader.setCredentials(credentials);
//		requestHeader.setMessageID(messageId);
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
