package com.coopbank.useraccessservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "cisl_user_temp")
@Getter
@Setter
public class UserTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEMP_ID")
    private Long tempId;

    @Column(name = "MODIFICATION_TYPE")
    private String modificationType; // creation

    // FIELDS NEEDING CHANGE
    @Column(name = "FIRST_NAME", length = 100)
    private String firstName;

    @Column(name = "LAST_NAME", length = 100)
    private String lastName;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Roles role;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Departments department;

    @Column(name = "STATUS_APPROVAL")
    private Long statusApproval; // 0L/"PENDING", 1L/"APPROVED", 2L/"REJECTED"

    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreationTimestamp
    @Column(name = "DATE_CREATED", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dateCreated;

    @Column(name = "APPROVED_BY")
    private String approvedBy;

    @Column(name = "APPROVAL_DATE")
    private Timestamp approvalDate;

    @Column(name = "REJECTION_REASON")
    private String rejectionReason;

    @Column(name = "REQUEST_ID")
    private Long requestId;
}