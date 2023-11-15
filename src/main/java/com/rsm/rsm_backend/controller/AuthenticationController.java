package com.rsm.rsm_backend.controller;

import com.rsm.rsm_backend.authenticationDTO.AuthenticationRequestDTO;
import com.rsm.rsm_backend.authenticationDTO.AuthenticationResponseDTO;
import com.rsm.rsm_backend.authenticationDTO.RegisterRequestDTO;
import com.rsm.rsm_backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseDTO> register(
            @RequestBody RegisterRequestDTO request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseDTO> login(
            @RequestBody AuthenticationRequestDTO request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
