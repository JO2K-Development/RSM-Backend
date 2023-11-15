package com.rsm.rsm_backend.utilities.data_initialization;

import com.rsm.rsm_backend.authenticationDTO.RegisterRequestDTO;
import com.rsm.rsm_backend.service.AuthenticationService;
import com.rsm.rsm_backend.service.JwtService;
import com.rsm.rsm_backend.service.ProviderService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ProviderDataInitializer {
    private final ProviderService providerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    @Autowired
    public ProviderDataInitializer(ProviderService providerService, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, AuthenticationService authenticationService) {
        this.providerService = providerService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    public void init() {
        RegisterRequestDTO registerRequestDTO = RegisterRequestDTO
                .builder()
                .email("provider@gmail.com")
                .password("password").build();

        authenticationService.register(registerRequestDTO);

        System.out.println("Initial provider data: \n email: provider@gmail.com\n password: password");
    }
}
