package com.rsm.rsm_backend.controller;


import com.rsm.rsm_backend.entity.Provider;
import com.rsm.rsm_backend.service.ProviderService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/provider")
@CrossOrigin(origins = "http://localhost:3000")
public class ProviderController {
    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping(value = "/{id}")
    Provider getProviderById(@PathVariable String id){
        Optional<Provider> provider = providerService.getProviderById(id);
        if(provider.isEmpty())
            throw new NoSuchElementException();
        return provider.get();
    }

    @GetMapping()
    List<Provider> getAllProviders(){
        return providerService.getAllProviders();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    Provider addProvider(@RequestBody Provider provider){
        return providerService.addProvider(provider);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Provider updateProvider(@PathVariable String id, @RequestBody Provider updatedProvider){
        Optional<Provider> existingProvider = providerService.getProviderById(id);
        if(existingProvider.isEmpty())
            throw new NoSuchElementException();

        providerService.updateProvider(id, updatedProvider);

        return updatedProvider;
    }

    @DeleteMapping(value = "/{id}")
    void deleteProviderById(@PathVariable String id){
        providerService.deleteProvider(id);
    }
}
