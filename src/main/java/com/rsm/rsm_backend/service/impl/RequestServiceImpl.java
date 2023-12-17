package com.rsm.rsm_backend.service.impl;

import com.rsm.rsm_backend.entity.Provider;
import com.rsm.rsm_backend.entity.Request;
import com.rsm.rsm_backend.entity.RequestStatus;
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
    public List<Request> getVerifiedOrDoneRequestsWithoutProvider() {
        List<Request> requestList = requestRepository.findByProvider(null);
        requestList.removeIf(request -> !request.getIsVerified());
        requestList.removeIf(request -> request.getRequestStatus().equals(RequestStatus.DONE));
        return requestList;
    }


    @Override
    public List<Request> getRequestsByProviderId(String id) {
        Optional<Provider> provider = providerRepository.findById(id);

        if (provider.isEmpty()) return null;

        return requestRepository.findByProvider(providerRepository.findById(id).get());
    }

    @Override
    public List<Request> getRequestsByProviderEmail(String email) {
        Optional<Provider> provider = providerRepository.findByEmail(email);
        if (provider.isEmpty())
            return null;

        List<Request> requestList = requestRepository.findByProvider(provider.get());
        requestList.removeIf(request -> request.getRequestStatus().equals(RequestStatus.DONE));

        return requestList;
        //return provider.map(requestRepository::findByProvider).orElse(null);
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
