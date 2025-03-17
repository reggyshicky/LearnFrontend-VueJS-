package com.coopbank.useraccessservice.services.impl;

import com.coopbank.useraccessservice.dtos.BankUserDTO;
import com.coopbank.useraccessservice.entities.BankUsers;
import com.coopbank.useraccessservice.entities.Departments;
import com.coopbank.useraccessservice.entities.Roles;
import com.coopbank.useraccessservice.entities.UserTemp;
import com.coopbank.useraccessservice.exceptions.ItemNotFoundException;
import com.coopbank.useraccessservice.repositories.*;
import com.coopbank.useraccessservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserTempRepository userTempRepository;
    private final RoleRepository roleRepository;
    private final RoleTempRepository roleTempRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public BankUsers findByUsername(String username) {
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new ItemNotFoundException("Bank user not found"));
    }

    @Override
    public String createBankUserTemp(BankUserDTO data) {
        BankUsers existing = userRepository.getByEmail(data.getEmail());
        if (existing != null) {
            throw new IllegalArgumentException("Bank user already exists");
        }

        Roles role = roleRepository.findByRoleId(data.getRoleId())
                .orElseThrow(() -> new ItemNotFoundException("Role not found"));

        Departments department = departmentRepository.findByDepartmentId(data.getDepartmentId())
                .orElseThrow(() -> new ItemNotFoundException("Department not found"));

        UserTemp temp = new UserTemp();
        temp.setFirstName(data.getFirstName());
        temp.setLastName(data.getLastName());
        temp.setEmail(data.getEmail());
        temp.setDepartment(department);
        temp.setRole(role);
        userTempRepository.save(temp);

        return "User " + temp.getFirstName() + " " + temp.getLastName() + " created pending approval";
    }

    @Override
    public String createBankUser(UserTemp data) {
        BankUsers bankUser = new BankUsers();
        bankUser.setFirstName(data.getFirstName());
        bankUser.setLastName(data.getLastName());
        bankUser.setEmail(data.getEmail());
        bankUser.setRole(data.getRole());
        bankUser.setDepartment(data.getDepartment());
        bankUser.setCreatedBy(data.getCreatedBy());

        userRepository.save(bankUser);
        return "";
    }
}
