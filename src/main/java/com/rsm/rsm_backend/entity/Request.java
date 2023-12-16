package com.rsm.rsm_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@Builder
@Entity
@Table(name = "request")
public class Request {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Builder.Default
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client creator = new Client();

    @Builder.Default
    @Column(name = "message")
    private String message = "message";

    @Builder.Default
    @Column(name = "car_make")
    private String carMake = "car_make";

    @Builder.Default
    @Column(name = "car_model")
    private String carModel = "car_model";

    @Builder.Default
    @Column(name = "car_year")
    private Integer carYear = 1900;

    @Builder.Default
    @Column(name = "creation_date")
    private Date creationDate = new Date(System.currentTimeMillis());

    @Builder.Default
    @Column(name = "pickup_date")
    private Date pickupDate = null;

    @Builder.Default
    @Column(name = "diagnosis")
    private String diagnosis = "";

    @Builder.Default
    @Column(name = "delivery_date")
    private Date deliveryDate = null;

    @Builder.Default
    @Column(name = "licence_plate_number")
    private String licencePlateNumber = "licence_plate_number";

    @Builder.Default
    @Column(name = "repair_estimation")
    private Integer repairEstimation = 0;

    @Builder.Default
    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Builder.Default
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "provider_id")
    private Provider provider = null;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    private RequestStatus requestStatus = RequestStatus.WAITING_FOR_AN_EMAIL_VERIFICATION;

}
