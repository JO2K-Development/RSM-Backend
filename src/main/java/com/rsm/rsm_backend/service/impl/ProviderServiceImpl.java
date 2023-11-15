package com.rsm.rsm_backend.service.impl;

import com.rsm.rsm_backend.entity.Provider;
import com.rsm.rsm_backend.repository.ProviderRepository;
import com.rsm.rsm_backend.service.ProviderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }


    @Override
    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    @Override
    public Optional<Provider> getProviderById(String id) {
        return providerRepository.findById(id);
    }

    @Override
    public Optional<Provider> getProviderByEmail(String email) {
        return providerRepository.findByEmail(email);
    }

    @Override
    public Provider addProvider(Provider provider) {
        if (providerRepository.findByEmail(provider.getEmail()).isPresent())
            throw new RuntimeException("Username already exists");
        return providerRepository.save(provider);
    }

    @Override
    public Provider updateProvider(String id, Provider updatedProvider) {
        updatedProvider.setId(id);
        return providerRepository.save(updatedProvider);
    }

    @Override
    public void deleteProvider(String id) {
        providerRepository.deleteById(id);
    }
}
