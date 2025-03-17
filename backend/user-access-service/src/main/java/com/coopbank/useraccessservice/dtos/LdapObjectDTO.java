package com.coopbank.useraccessservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LdapObjectDTO {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String department;

    public LdapObjectDTO(String firstName, String lastName, String emailAddress, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.department = department;
    }
}