package com.coopbank.useraccessservice.repositories;

import com.coopbank.useraccessservice.entities.BankUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BankUsers, Long> {
    BankUsers getByEmail(String email);
    Optional<BankUsers> findByUserName(String username);
}
