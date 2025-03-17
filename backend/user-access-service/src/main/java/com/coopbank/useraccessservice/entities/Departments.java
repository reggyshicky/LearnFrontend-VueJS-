package com.coopbank.useraccessservice.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "cisl_department")
@Getter
@Setter
public class Departments {
    @Id
    @GeneratedValue
    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "description")
    private String description;

    @Column(name = "department_head")
    private String departmentHead;

    @CreationTimestamp
    @Column(name = "date_created")
    private Timestamp dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "created_by")
    private BankUsers createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "status_id")
    private Status statusId;
}
