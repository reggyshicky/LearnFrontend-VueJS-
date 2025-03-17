package com.coopbank.useraccessservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cisl_status")
@Getter
@Setter
public class Status {
    @Id
    @GeneratedValue
    @Column(name = "STATUS_ID")
    private long statusId;

    @Column(name = "STATUS_NAME")
    private String statusName;

    @Column(name = "STATUS_DESCRIPTION")
    private String statusDescription;
}
