package com.rsm.rsm_backend.controller;


import com.rsm.rsm_backend.entity.Provider;
import com.rsm.rsm_backend.entity.Request;
import com.rsm.rsm_backend.service.ProviderService;
import com.rsm.rsm_backend.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/provider")
@CrossOrigin(origins = "http://localhost:3000")
public class ProviderController {
    private final ProviderService providerService;
    private final RequestService requestService;

    public ProviderController(ProviderService providerService, RequestService requestService) {
        this.providerService = providerService;
        this.requestService = requestService;
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Provider> getProviderById(@PathVariable String id){
        Optional<Provider> provider = providerService.getProviderById(id);
        if(provider.isEmpty())
            return ResponseEntity.badRequest().build();

        return new ResponseEntity<>(provider.get(), HttpStatus.OK );
    }

    @GetMapping()
    ResponseEntity<List<Provider>> getAllProviders(){
        return new ResponseEntity<>(providerService.getAllProviders(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Provider> addProvider(@RequestBody Provider provider){
        return new ResponseEntity<>(providerService.addProvider(provider), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Provider> updateProvider(@PathVariable String id, @RequestBody Provider updatedProvider){
        Optional<Provider> existingProvider = providerService.getProviderById(id);
        if(existingProvider.isEmpty())
            throw new NoSuchElementException();

        return new ResponseEntity<>(providerService.updateProvider(id, updatedProvider), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Provider> deleteProviderById(@PathVariable String id){
        Optional<Provider> provider = providerService.getProviderById(id);
        if(provider.isEmpty())
            return ResponseEntity.badRequest().build();

        providerService.deleteProvider(id);
        return new ResponseEntity<>(provider.get(), HttpStatus.OK);
    }

    @PutMapping(value="/assign/{provider_id}")
    ResponseEntity<Provider> assignRequestToProvider(@PathVariable String provider_id, @RequestParam String request_id){

        Optional<Provider> provider = providerService.getProviderById(provider_id);
        Optional<Request> request = requestService.getRequestById(request_id);
        if(request.isEmpty() || provider.isEmpty())
            return ResponseEntity.badRequest().build();

        request.get().setProvider(provider.get());

        requestService.updateRequest(request_id, request.get());
        return ResponseEntity.ok().build();

    }

    @GetMapping(value = "/assigned/{id}")
    ResponseEntity<List<Request>> getAllRequestsOfProvider(@PathVariable String id){
        if(providerService.getProviderById(id).isEmpty())
            return ResponseEntity.badRequest().build();
        return new ResponseEntity<>(providerService.getRequestsByProviderId(id), HttpStatus.OK);
    }
}
