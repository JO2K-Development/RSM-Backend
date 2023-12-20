package com.rsm.rsm_backend.DTO.authenticationDTO;

import lombok.*;



@Builder
public record RegisterRequestDTO(
        String firstname,
        String lastname,
        String email,
        String password,
        String phoneNumber) {


}
