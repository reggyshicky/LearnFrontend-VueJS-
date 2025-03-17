package com.coopbank.useraccessservice.repositories;

import com.coopbank.useraccessservice.entities.UserTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTempRepository extends JpaRepository<UserTemp, Long> {
}
