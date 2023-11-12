package com.rsm.rsm_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "provider")
@Setter
@Getter
public class Provider {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "e_mail")
    private String eMail;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "provider_id")
    private List<Request> requestList;
}
