package com.rsm.rsm_backend.controller;


import com.rsm.rsm_backend.DTO.ProviderDTO;
import com.rsm.rsm_backend.entity.Provider;
import com.rsm.rsm_backend.service.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/provider")
@CrossOrigin
public class ProviderController {
    private final ProviderService providerService;
    private final PasswordEncoder passwordEncoder;

    public ProviderController(ProviderService providerService, PasswordEncoder passwordEncoder) {
        this.providerService = providerService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Provider> getProviderById(@PathVariable String id) {
        Optional<Provider> provider = providerService.getProviderById(id);
        return provider.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/getbyemail/{email}")
    ResponseEntity<Provider> getProviderByEmail(@PathVariable String email) {
        Optional<Provider> provider = providerService.getProviderByEmail(email);
        return provider.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }


    @GetMapping()
    ResponseEntity<List<Provider>> getAllProviders() {
        return new ResponseEntity<>(providerService.getAllProviders(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Provider> addProvider(@RequestBody Provider provider) {
        if (providerService.getProviderByEmail(provider.getEmail()).isPresent())
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);

        provider.setPassword(passwordEncoder.encode(provider.getPassword()));
        return new ResponseEntity<>(providerService.addProvider(provider), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Provider> updateProvider(@PathVariable String id, @RequestBody Provider updatedProvider) {
        Optional<Provider> existingProvider = providerService.getProviderById(id);
        if (existingProvider.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        updatedProvider.setPassword(existingProvider.get().getPassword());

        return new ResponseEntity<>(providerService.updateProvider(id, updatedProvider), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Provider> patchProvider(@PathVariable String id, @RequestBody ProviderDTO updatedProvider) {
        Optional<Provider> existingProvider = providerService.getProviderById(id);
        if (existingProvider.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        Provider provider = existingProvider.get();
        if (updatedProvider.firstName() != null)
            provider.setFirstName(updatedProvider.firstName());
        if (updatedProvider.lastName() != null)
            provider.setLastName(updatedProvider.lastName());
        if (updatedProvider.address() != null)
            provider.setAddress(updatedProvider.address());
        if (updatedProvider.phoneNumber() != null)
            provider.setPhoneNumber(updatedProvider.phoneNumber());


        return new ResponseEntity<>(providerService.updateProvider(id, provider), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Provider> deleteProviderById(@PathVariable String id) {
        Optional<Provider> provider = providerService.getProviderById(id);
        if (provider.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        providerService.deleteProvider(id);
        return new ResponseEntity<>(provider.get(), HttpStatus.OK);
    }


}
