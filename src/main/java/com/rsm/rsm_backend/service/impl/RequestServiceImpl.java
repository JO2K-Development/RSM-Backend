package com.rsm.rsm_backend.service.impl;

import com.rsm.rsm_backend.entity.Provider;
import com.rsm.rsm_backend.entity.Request;
import com.rsm.rsm_backend.repository.ProviderRepository;
import com.rsm.rsm_backend.repository.RequestRepository;
import com.rsm.rsm_backend.service.RequestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final ProviderRepository providerRepository;

    public RequestServiceImpl(RequestRepository requestRepository, ProviderRepository providerRepository) {
        this.requestRepository = requestRepository;
        this.providerRepository = providerRepository;
    }


    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @Override
    public Optional<Request> getRequestById(String id) {
        return requestRepository.findById(id);
    }



   @Override
   public List<Request> getRequestWithoutProvider() {
        return requestRepository.findByProvider(null);
    }


    @Override
    public List<Request> getRequestsByProviderId(String id) {
        Optional<Provider> provider = providerRepository.findById(id);

        if(provider.isEmpty())
            return null;

        return requestRepository.findByProvider(providerRepository.findById(id).get());
    }

    @Override
    public List<Request> getRequestsByProviderEmail(String email) {
        Optional<Provider> provider = providerRepository.findByEmail(email);

        return provider.map(requestRepository::findByProvider).orElse(null);
    }

    @Override
    public Request addRequest(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public Request updateRequest(String id, Request updatedRequest) {
        updatedRequest.setId(id);
        return requestRepository.save(updatedRequest);
    }

    @Override
    public void deleteRequest(String id) {
        requestRepository.deleteById(id);
    }
}
