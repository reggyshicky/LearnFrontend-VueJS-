package com.coopbank.admin.administrative_service.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

import javax.xml.transform.dom.DOMResult;

import org.apache.logging.log4j.Logger;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPHeader;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPPart;

public class SoaClientUtil {

	public static ClientInterceptor intercept(Map<String, String> headers, Logger log) {
		return new ClientInterceptor() {
			
		    @Override
		    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		        try {				
					SaajSoapMessage request = (SaajSoapMessage) messageContext.getRequest();
					SOAPMessage saajMessage = request.getSaajMessage();
					SOAPPart soapPart = saajMessage.getSOAPPart();
					SOAPEnvelope envelope = soapPart.getEnvelope();

					envelope.removeNamespaceDeclaration("SOAP-ENV");
					envelope.addNamespaceDeclaration("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
					envelope.setPrefix("soapenv");

					SOAPHeader header1 = envelope.getHeader();
					header1.setPrefix("soapenv");
					
		            messageContext.getRequest().writeTo(outputStream);
		            String requestContent = outputStream.toString(StandardCharsets.UTF_8.name());
		            log.info("Request: {}", requestContent);
		            
		        } catch (IOException | SOAPException e) {
		        	log.error("IOException/SOAPException occurred on handleRequest: ", e);
		        } finally {
		            try {
		                outputStream.close();
		            } catch (IOException e) {
		                log.warn("Error closing output stream", e);
		            }
		        }
		        return true;
		    }

		    @Override
		    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
		    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		        try {
		            messageContext.getResponse().writeTo(outputStream);
		            String responseContent = outputStream.toString(StandardCharsets.UTF_8.name());
		            log.info("Response: {}", responseContent);


		            SoapHeader soapHeader = ((SoapMessage) messageContext.getResponse()).getSoapHeader();
		            Iterator<SoapHeaderElement> soapHeaderElementIterator = soapHeader.examineAllHeaderElements();

		            while (soapHeaderElementIterator.hasNext()) {
		                Object element = soapHeaderElementIterator.next();
		                if (!(element instanceof SoapHeaderElement)) {
		                    continue;
		                }

		                SoapHeaderElement headerElement = (SoapHeaderElement) element;
		                if (!(headerElement.getResult() instanceof DOMResult)) {
		                    continue;
		                }

		                Node rootNode = ((DOMResult) headerElement.getResult()).getNode();
		                processNode(rootNode, headers, "");
		            }
		          for(var header : headers.entrySet()) {
		          	log.debug(header.getKey() + ": " +header.getValue());
		          }
		        } catch (Exception e) {
		            log.error("Exception ", e);
		        } finally {
		            try {
		                outputStream.close();
		            } catch (IOException e) {
		                log.error("Error closing output stream", e);
		            }
		        }
		        return true;
		    }

		    @Override
		    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
		    	final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		    	try {
					messageContext.getResponse().writeTo(outputStream);
					final String payload = outputStream.toString(StandardCharsets.UTF_8.name());
					log.info(payload);
				} catch (final IOException e) {
					throw new WebServiceClientException("Can not write the SOAP fault into the out stream", e) {
						private static final long serialVersionUID = 3538336091916808141L;
					};
				} finally {
		            try {
		                outputStream.close();
		            } catch (IOException e) {
		                log.error("Error closing output stream", e);
		            }
		        }
		    	return true;
		    }

		    @Override
		    public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {
		    	if(e != null)
		    		log.error("Exception occurred on afterCompletion: ", e);
		    }
		};
	}  
	
    private static void processNode(Node node, Map<String, String> headers, String prefix) {
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = childNode.getNodeName();
                String newPrefix = prefix.isEmpty() ? nodeName : prefix + "." + nodeName;

                if (!childNode.hasChildNodes() || (childNode.getChildNodes().getLength() == 1 && childNode.getFirstChild().getNodeType() == Node.TEXT_NODE)) {
                    String nodeValue = childNode.getTextContent().trim();
                    if (!nodeValue.isEmpty()) {
                        headers.put(newPrefix, nodeValue);
                    }
                } else {
                    processNode(childNode, headers, newPrefix);
                }
            }
        }
    }

}
