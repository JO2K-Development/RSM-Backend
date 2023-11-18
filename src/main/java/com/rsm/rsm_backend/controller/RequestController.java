package com.rsm.rsm_backend.controller;

import com.rsm.rsm_backend.entity.Provider;
import com.rsm.rsm_backend.entity.Request;
import com.rsm.rsm_backend.entity.RequestStatus;
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
@RequestMapping("/api/v1/request")
@CrossOrigin(origins = "http://localhost:3000")
public class RequestController {

    private final RequestService requestService;
    private final ProviderService providerService;


    public RequestController(RequestService requestService, ProviderService providerService) {
        this.requestService = requestService;
        this.providerService = providerService;
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Request> getRequestById(@PathVariable String id) {

        Optional<Request> request = requestService.getRequestById(id);
        if (request.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(request.get(), HttpStatus.OK);
    }

    @GetMapping()
    ResponseEntity<List<Request>> getAllRequests() {
        return new ResponseEntity<>(requestService.getAllRequests(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Request> updateRequest(@PathVariable String id, @RequestBody Request updatedRequest) {

        Optional<Request> existingRequest = requestService.getRequestById(id);
        if (existingRequest.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(requestService.updateRequest(id, updatedRequest), HttpStatus.OK);
    }

    @PutMapping(value = "/pair/{request_id}")
    ResponseEntity<Request> pairRequestWithProvider(@PathVariable String request_id, @RequestParam String provider_id) {

        Optional<Provider> provider = providerService.getProviderById(provider_id);
        Optional<Request> request = requestService.getRequestById(request_id);
        if (request.isEmpty() || provider.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        request.get().setProvider(provider.get());

        return new ResponseEntity<>(requestService.updateRequest(request_id, request.get()), HttpStatus.OK);
    }

    @PutMapping(value = "/unpair/{id}")
    ResponseEntity<Request> unpairRequestWithProvider(@PathVariable String id) {

        Optional<Request> request = requestService.getRequestById(id);
        if (request.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        request.get().setProvider(null);

        return new ResponseEntity<>(requestService.updateRequest(id, request.get()), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    Request addRequest(@RequestBody Request request) {
        request.setRequestStatus(RequestStatus.WAITING_FOR_AN_EMAIL_VERIFICATION);
        return requestService.addRequest(request);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Request> deleteRequestById(@PathVariable String id) {
        Optional<Request> request = requestService.getRequestById(id);

        if (request.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        requestService.deleteRequest(id);
        return new ResponseEntity<>(request.get(), HttpStatus.OK);
    }


}
