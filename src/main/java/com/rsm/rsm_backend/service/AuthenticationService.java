package com.rsm.rsm_backend.service;

import com.rsm.rsm_backend.authenticationDTO.AuthenticationRequest;
import com.rsm.rsm_backend.authenticationDTO.AuthenticationResponse;
import com.rsm.rsm_backend.authenticationDTO.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);


}
