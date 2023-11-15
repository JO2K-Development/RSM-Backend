package com.rsm.rsm_backend.service;

import com.rsm.rsm_backend.entity.Request;

import java.util.List;
import java.util.Optional;

public interface RequestService {

    List<Request> getAllRequests();
    Optional<Request> getRequestById(String id);
    Request addRequest(Request request);
    Request updateRequest(String id, Request request);
    void deleteRequest(String id);
}
