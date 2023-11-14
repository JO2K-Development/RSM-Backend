package com.rsm.rsm_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;


@Entity
@Table(name = "request")
@Data
@ToString
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client creator;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private Date date;



}
