package com.coopbank.admin.administrative_service.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.coopbank.admin.administrative_service.dtos.Entity;
import com.coopbank.admin.administrative_service.dtos.Person;
import com.coopbank.admin.administrative_service.dtos.SanctionDetailsSoaResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SanctionDetailsParser extends DefaultHandler {

	private StringBuilder currentValue = new StringBuilder();
	private SanctionDetailsSoaResponse response;
	private Person currentPerson;
	private Entity currentEntity;

	public SanctionDetailsSoaResponse parse(String xmlContent) {
		// TODO - can SAXParser be a singleton bean?
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser;
		try {
			saxParser = factory.newSAXParser();
			response = new SanctionDetailsSoaResponse();
			saxParser.parse(new InputSource(new StringReader(xmlContent)), this);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			var message = "Unable to parse soa response";
			log.error(message, e);
			throw new RuntimeException(message +": ",  e);
		}
		return response;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		currentValue.setLength(0);
		var idx = qName.indexOf(":");
		String name = qName.substring(idx + 1);
		if ("Person".equals(name)) {
			currentPerson = new Person();
		} else if ("Entity".equals(name)) {
			currentEntity = new Entity();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		var idx = qName.indexOf(":");
		String name = qName.substring(idx + 1);
		switch (name) {
		case "CorrelationID":
			response.setCorrelationID(currentValue.toString());
			break;
		case "ElapsedTime":
			response.setElapsedTime(currentValue.toString());
			break;
		case "MessageID":
			response.setMessageID(currentValue.toString());
			break;
		case "StatusCode":
			response.setStatusCode(currentValue.toString());
			break;
		case "StatusDescription":
			response.setStatusDescription(currentValue.toString());
			break;
		case "StatusDescriptionKey":
			response.setStatusDescriptionKey(currentValue.toString());
			break;
		case "ApplicationID":
			response.setApplicationID(currentValue.toString());
			break;
		case "MessageCode":
			response.setMessageCode(currentValue.toString());
			break;
		case "MessageDescription":
			response.setMessageDescription(currentValue.toString());
			break;
		case "MessageType":
			response.setMessageType(currentValue.toString());
			break;
		case "NumberOfMatches":
			response.setNumberOfMatches(Integer.parseInt(currentValue.toString()));
			break;
		case "Person":
			if (response.getPersons() == null) {
				response.setPersons(new ArrayList<>());
			}
			response.getPersons().add(currentPerson);
			currentPerson = null;
			break;
		case "Entity":
			if (response.getEntities() == null) {
				response.setEntities(new ArrayList<>());
			}
			response.getEntities().add(currentEntity);
			currentEntity = null;
			break;
		default:
			handlePersonOrEntityField(qName);
			break;
		}
	}

	private void handlePersonOrEntityField(String qName) {
		if (currentPerson != null) {
			var idx = qName.indexOf(":");
			String name = qName.substring(idx + 1);
			switch (name) {
			case "IdentificationDocNo":
				currentPerson.setIdentificationDocNo(currentValue.toString());
				break;
			case "IdentificationDocType":
				currentPerson.setIdentificationDocType(currentValue.toString());
				break;
			case "Name":
				currentPerson.setName(currentValue.toString());
				break;
			case "Address":
				currentPerson.setAddress(currentValue.toString());
				break;
			case "Nationality":
				currentPerson.setNationality(currentValue.toString());
				break;
			case "DateofBirth":
				currentPerson.setDateofBirth(currentValue.toString());
				break;
			case "CountryofBirth":
				currentPerson.setCountryofBirth(currentValue.toString());
				break;
			case "ListedBy":
				currentPerson.setListedBy(currentValue.toString());
				break;
			case "MatchScore":
				currentPerson.setMatchScore(currentValue.toString());
				break;
			}
		} else if (currentEntity != null) {
			// Handle Entity fields similarly to Person fields
			// ...
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		currentValue.append(ch, start, length);
	}
}
