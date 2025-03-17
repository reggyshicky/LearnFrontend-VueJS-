package com.coopbank.useraccessservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BankUserDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Long roleId;
    private Long departmentId;
}
