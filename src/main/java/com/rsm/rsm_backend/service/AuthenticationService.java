package com.rsm.rsm_backend.service;

import com.rsm.rsm_backend.authenticationDTO.AuthenticationRequestDTO;
import com.rsm.rsm_backend.authenticationDTO.AuthenticationResponseDTO;
import com.rsm.rsm_backend.authenticationDTO.RegisterRequestDTO;

public interface AuthenticationService {

    AuthenticationResponseDTO register(RegisterRequestDTO request);
    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);


}
