package com.coopbank.useraccessservice.services;

import com.coopbank.useraccessservice.dtos.BankUserDTO;
import com.coopbank.useraccessservice.entities.BankUsers;
import com.coopbank.useraccessservice.entities.UserTemp;

public interface UserService {

    BankUsers findByUsername(String username);

    String createBankUserTemp(BankUserDTO data);
    String createBankUser(UserTemp data);
}
