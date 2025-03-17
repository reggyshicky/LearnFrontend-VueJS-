package com.coopbank.useraccessservice.dtos;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class LdapAuthResponseDTO {

    private boolean authenticated;
    private LdapObjectDTO ldapObject;

    public LdapAuthResponseDTO(boolean authenticated, LdapObjectDTO ldapObject) {
        this.authenticated = authenticated;
        this.ldapObject = ldapObject;
    }

}



