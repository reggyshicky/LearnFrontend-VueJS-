package com.coopbank.admin.administrative_service.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CalendarUtil {

    public static XMLGregorianCalendar convert(LocalDateTime localDateTime) {
        XMLGregorianCalendar calendar = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String formattedDate = localDateTime.format(formatter);
            calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(formattedDate);
        } catch (Exception e) {
            log.error("Failed converting to XMLGregorianCalendar!", e);
        }
        return calendar;
    }
}
