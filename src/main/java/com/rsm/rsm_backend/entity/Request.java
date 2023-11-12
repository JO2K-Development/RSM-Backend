package com.rsm.rsm_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Entity
@Table(name = "request")
@Setter
@Getter
public class Request {

    @Id
    @Column(name = "id")
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client creator;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private Date date;



}
