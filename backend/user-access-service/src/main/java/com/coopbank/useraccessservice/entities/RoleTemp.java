package com.coopbank.useraccessservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cisl_role_temp")
@Getter
@Setter
public class RoleTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEMP_ID")
    private Long tempId;
}
