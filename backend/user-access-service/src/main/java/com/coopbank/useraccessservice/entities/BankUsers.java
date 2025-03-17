package com.coopbank.useraccessservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "cisl_bank_users")
@Getter @Setter
public class BankUsers {
    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USER_NAME", unique = true)
    private String userName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name= "EMAIL")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name= "DEPARTMENT")
    private Departments department;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Roles role;

    @Column(name = "OTP")
    private String otp;

    @Column(name = "OTP_EXPIRY_TIME")
    private Timestamp otpExpiryTime;

    @CreationTimestamp
    @Column(name = "DATE_CREATED", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dateCreated;

    @Column(name = "CREATED_BY")
    private String createdBy;



}

