package com.rsm.rsm_backend.DTO.authenticationDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record AuthenticationRequestDTO(
        String email,
        String password) {
}
