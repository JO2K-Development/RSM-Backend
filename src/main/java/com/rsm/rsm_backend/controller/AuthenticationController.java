package com.rsm.rsm_backend.controller;

import com.rsm.rsm_backend.DTO.authenticationDTO.AuthenticationRequestDTO;
import com.rsm.rsm_backend.DTO.authenticationDTO.AuthenticationResponseDTO;
import com.rsm.rsm_backend.DTO.authenticationDTO.RegisterRequestDTO;
import com.rsm.rsm_backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
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
