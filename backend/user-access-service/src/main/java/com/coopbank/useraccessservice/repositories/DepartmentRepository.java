package com.coopbank.useraccessservice.repositories;

import com.coopbank.useraccessservice.entities.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Departments, Long> {
    Optional<Departments> findByDepartmentId(Long id);
}
