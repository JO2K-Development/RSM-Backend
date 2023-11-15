package com.rsm.rsm_backend.service.impl;

import com.rsm.rsm_backend.entity.Request;
import com.rsm.rsm_backend.repository.RequestRepository;
import com.rsm.rsm_backend.service.RequestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
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
