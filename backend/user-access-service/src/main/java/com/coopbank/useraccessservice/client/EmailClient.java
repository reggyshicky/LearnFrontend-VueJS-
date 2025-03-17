package com.coopbank.useraccessservice.client;


import lombok.RequiredArgsConstructor;
import org.apache.xmlbeans.impl.soap.SOAPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class EmailClient {
    private static final String SUCCESS_CODE = "S";
    private static final String MESSAGE_TYPE_TAG = "ns0:MessageType";
    private static final String MESSAGE_DESCRIPTION_TAG = "ns0:MessageDescription";
    private static final Logger logger = LoggerFactory.getLogger(EmailClient.class);

    @Value("${email.endpoint.preprod.url}")
    private String soapEndpointUrl;

    @Value("${email.endpoint.username}")
    private String soapUsername;

    @Value("${email.endpoint.password}")
    private String soapPassword;

    @Value("${soa.truststore.path}")
    private String soaTruststorePath;

    @Value("${soa.truststore.password}")
    private String soaTruststorePassword;

    private final DocumentBuilderFactory documentBuilderFactory;
    private final DocumentBuilder documentBuilder;

    public EmailClient() {
        try {
            this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
            this.documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Failed to initialize document builder", e);
        }
    }

    public void sendEmail(String to, String subject, String body) throws SOAPException {
        try {
            ResponseEntity<String> acctLinkResponse = sendSoapRequest(to, subject, body);
            validateResponse(acctLinkResponse);
            logger.info(acctLinkResponse.getBody());
            Document doc = parseResponseBody(acctLinkResponse.getBody());
            ResponseStatus status = extractResponseStatus(doc);
            logger.info("Status code: {}", status.code);
            logger.info("Description: {}", status.description);
            String description = status.description;
            boolean success = status.isSuccess();
            logger.info("Success status: {}", description);

            if (!success) {
                logger.error("Throwing SOAPException with description: {}", description);
                throw new SOAPException("description");
            }
        } catch (SOAPException e) {
            logger.error("Error sending email", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error sending email", e);
            throw new SOAPException("Unexpected error occurred", e);
        }
    }

    private ResponseEntity<String> sendSoapRequest(String to, String subject, String body) {
        String soapRequest = createSoapRequest(to, subject, body);

        RestTemplate restTemplate = new RestTemplate();
//        HttpComponentsClientHttpRequestFactory requestFactory = getRequestFactory();
//        restTemplate.setRequestFactory(requestFactory);

        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(soapRequest, headers);

        return restTemplate.exchange(soapEndpointUrl, HttpMethod.POST, entity, String.class);
    }

    private String createSoapRequest(String to, String subject, String body) {
        // Construct the SOAP request envelope
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"urn://co-opbank.co.ke/SharedResources/Schemas/SOAMessages/SoapHeader\" xmlns:dat=\"urn://co-opbank.co.ke/Banking/Common/Service/CommonEmail/Send/1.0/DataIO\" xmlns:com=\"urn://co-opbank.co.ke/Banking/Common/DataModel/CommonEmail/Send/1.0/CommonEmail.send\" xmlns:ns=\"urn://co-opbank.co.ke/Banking/CanonicalDataModel/Email/1.0\">\n"
                + "   <soapenv:Header>\n"
                + "      <soap:HeaderRequest>\n"
                + "         <soap:MessageID>" + java.util.UUID.randomUUID() + "</soap:MessageID>         \n"
                + "         <soap:CorrelationID>" + java.util.UUID.randomUUID() + "</soap:CorrelationID>         \n"
                + "         <soap:Credentials>\n"
                + "            <soap:SystemCode>010</soap:SystemCode>           \n"
                + "         </soap:Credentials>\n"
                + "      </soap:HeaderRequest>\n"
                + "   </soapenv:Header>\n"
                + "   <soapenv:Body>\n"
                + "      <dat:DataInput>\n"
                + "         <com:sendInput>\n"
                + "            <ns:Email>\n"
                + "               <ns:From>ILP@co-opbank.co.ke</ns:From>               \n"
                + "               <ns:To>" + to + "</ns:To>               \n"
                + "               <ns:Subject>" + subject + "</ns:Subject>               \n"
                + "                 <ns:Message><![CDATA[                           \n"
                +                        body                    +           "\n"
                + "                 ]]></ns:Message>                           \n"
                + "            </ns:Email>\n"
                + "         </com:sendInput>\n"
                + "      </dat:DataInput>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";
    }

    private HttpHeaders createHeaders() {
        String encodedAuth = "Basic " + Base64
                .getEncoder()
                .encodeToString(String.format("%s:%s", soapUsername, soapPassword).getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", encodedAuth);
        headers.add("SoapAction", "\"/co-opbank.co.ke/Banking/Common/Service/CommonEmail/Send/1.0\"");
        headers.setContentType(org.springframework.http.MediaType.TEXT_XML);
        return headers;
    }

    private HttpComponentsClientHttpRequestFactory getRequestFactory() {
        return GlobalMethod.getRequestFactory(soaTruststorePath, soaTruststorePassword);
    }

    private void validateResponse(ResponseEntity<?> response) throws SOAPException {
        if (response == null) {
            throw new IllegalArgumentException("Response cannot be null");
        }

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new SOAPException("Unexpected HTTP status: " + response.getStatusCode());
        }
    }

    private Document parseResponseBody(String responseBody) throws SOAPException {
        try {
            InputStream inputStream = new ByteArrayInputStream(responseBody.getBytes());
            return documentBuilder.parse(inputStream);
        } catch (Exception e) {
            throw new SOAPException("Failed to parse response body", e);
        }
    }

    private ResponseStatus extractResponseStatus(Document doc) throws SOAPException {
        String statusCode = getTagContent(doc, MESSAGE_TYPE_TAG);
        String description = getTagContent(doc, MESSAGE_DESCRIPTION_TAG);

        if (statusCode == null) {
            throw new SOAPException("Status code is missing from response");
        }

        return new ResponseStatus(statusCode, description);
    }

    private String getTagContent(Document doc, String tagName) {
        NodeList nodes = doc.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            Element element = (Element) nodes.item(0);
            return element.getTextContent();
        }
        return null;
    }

    private record ResponseStatus(String code, String description) {
        public boolean isSuccess() {
            return SUCCESS_CODE.equals(code);
        }
    }
}
