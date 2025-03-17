package com.coopbank.useraccessservice.repositories;

import com.coopbank.useraccessservice.entities.RoleTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleTempRepository extends JpaRepository<RoleTemp, Long> {
}
