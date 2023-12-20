package com.rsm.rsm_backend.service.impl;

import com.rsm.rsm_backend.DTO.authenticationDTO.AuthenticationRequestDTO;
import com.rsm.rsm_backend.DTO.authenticationDTO.AuthenticationResponseDTO;
import com.rsm.rsm_backend.DTO.authenticationDTO.RegisterRequestDTO;
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
                .firstName(request.firstname())
                .lastName(request.lastname())
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ADMIN)
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
                        request.email(),
                        request.password()
                )
        );

        var user = providerService.getProviderByEmail(request.email())
                .orElseThrow();

        var jwtToken = jwtServiceImpl.generateToken(user);

        return AuthenticationResponseDTO
                .builder()
                .token(jwtToken)
                .build();
    }
}
