package com.rsm.rsm_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@Builder
@Entity
@DynamicUpdate
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Builder.Default
    @Column(name = "first_name")
    private String firstName = "first_name";

    @Builder.Default
    @Column(name = "last_name")
    private String lastName = "last_name";

    @Builder.Default
    @Column(name = "e_mail")
    private String email = "email@gmial.com";

    @Builder.Default
    @Column(name = "phone_number")
    private String phoneNumber = "phone_number";



}
