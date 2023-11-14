package com.rsm.rsm_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "client")
@Data
@ToString
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "e_mail")
    private String eMail;

    @Column(name = "phone_number")
    private String phoneNumber;
}
