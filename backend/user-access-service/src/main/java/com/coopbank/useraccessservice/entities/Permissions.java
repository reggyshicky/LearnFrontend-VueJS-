package com.coopbank.useraccessservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "cisl_permissions")
@Getter
@Setter
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERM_ID")
    private Long permId;

    @Column(name = "PERM_NAME", nullable = false)
    private String permName;

    @Column(name = "PERM_DESCRIPTION")
    private String permDescription;

    @Column(name = "CATEGORY_ID")
    private String categoryId;

    @CreationTimestamp
    @Column(name = "PERM_DATE_CREATED", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp permDateCreated;
}
