package com.rsm.rsm_backend.DTO;


import com.rsm.rsm_backend.entity.RequestStatus;
import jakarta.persistence.Enumerated;

import java.sql.Date;

public record RequestStatusDTO(@Enumerated RequestStatus requestStatus, Date pickupDate, Date deliveryDate, String diagnosis){

}

