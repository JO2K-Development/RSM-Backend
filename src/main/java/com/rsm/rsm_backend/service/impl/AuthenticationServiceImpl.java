package com.rsm.rsm_backend.service.impl;

import com.rsm.rsm_backend.authenticationDTO.AuthenticationRequestDTO;
import com.rsm.rsm_backend.authenticationDTO.AuthenticationResponseDTO;
import com.rsm.rsm_backend.authenticationDTO.RegisterRequestDTO;
import com.rsm.rsm_backend.entity.Provider;
import com.rsm.rsm_backend.entity.Role;
import com.rsm.rsm_backend.service.AuthenticationService;
import com.rsm.rsm_backend.service.JwtService;
import com.rsm.rsm_backend.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ProviderService providerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtServiceImpl;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        var user = Provider.builder()
                .firstName(request.getFirstname())
                .lastName(request.getFirstname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        providerService.addProvider(user);
        var jwtToken = jwtServiceImpl.generateToken(user);

        return AuthenticationResponseDTO
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = providerService.getProviderByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtServiceImpl.generateToken(user);

        return AuthenticationResponseDTO
                .builder()
                .token(jwtToken)
                .build();
    }
}
