package com.rsm.rsm_backend.service;

import com.rsm.rsm_backend.entity.Client;
import com.rsm.rsm_backend.entity.Provider;

import java.util.List;
import java.util.Optional;

public interface ProviderService {
    List<Provider> getAllProviders();
    Optional<Provider> getProviderById(String id);
    Optional<Provider> getProviderByEmail(String email);
    Provider addProvider(Provider provider);
    Provider updateProvider(String id, Provider updatedProvider);
    void deleteProvider(String id);
}
