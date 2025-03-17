package com.coopbank.useraccessservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "cisl_roles")
@Getter
@Setter
public class Roles {
    @Id
    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "ROLE_NAME", nullable = false)
    private String roleName;

    @Column(name = "ROLE_DESCRIPTION")
    private String roleDescription;

    @CreationTimestamp
    @Column(name = "ROLE_DATE_CREATED", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp roleDateCreated;

    @Column(name = "ROLE_CREATED_BY")
    private String roleCreatedBy;

    @Column(name = "ROLE_STATUS")
    private String roleStatus;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ROLE_PERMISSIONS",
            joinColumns = @JoinColumn(name = "ROLE_PM_ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_PM_PERM_ID")
    )
    private Set<Permissions> permissions;
}
