package com.rsm.rsm_backend.service;

import com.rsm.rsm_backend.DTO.authenticationDTO.AuthenticationRequestDTO;
import com.rsm.rsm_backend.DTO.authenticationDTO.AuthenticationResponseDTO;
import com.rsm.rsm_backend.DTO.authenticationDTO.RegisterRequestDTO;

public interface AuthenticationService {

    AuthenticationResponseDTO register(RegisterRequestDTO request);
    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);


}
