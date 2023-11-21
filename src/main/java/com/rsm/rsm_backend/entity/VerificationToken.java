package com.rsm.rsm_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@Builder
@Entity
@Table(name = "verification_token")
public class VerificationToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "token", unique = true)
    private String token;

    @CreationTimestamp
    @Column(name = "creation_time", updatable = false)
    private Date creationTime;

    @Column(name = "expiration_time", updatable = false)
    @Basic(optional = false)
    private Date expirationTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "request_id")
    private Request request;

    @Transient
    @Column(name = "is_expired")
    private boolean isExpired;

}
