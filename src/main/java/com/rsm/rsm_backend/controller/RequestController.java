package com.rsm.rsm_backend.controller;

import com.rsm.rsm_backend.entity.Provider;
import com.rsm.rsm_backend.entity.Request;
import com.rsm.rsm_backend.service.ProviderService;
import com.rsm.rsm_backend.service.RequestService;
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
    Request getRequestById(@PathVariable String id){
        Optional<Request> request = requestService.getRequestById(id);
        if(request.isEmpty())
            throw new NoSuchElementException();
        return request.get();
    }

    @GetMapping()
    List<Request> getAllRequests(){
        return requestService.getAllRequests();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Request updateRequest(@PathVariable String id, @RequestBody Request updatedRequest){
        Optional<Request> existingRequest = requestService.getRequestById(id);
        if(existingRequest.isEmpty())
            throw new NoSuchElementException();

        requestService.updateRequest(id, updatedRequest);

        return updatedRequest;
    }

    @PutMapping(value="/pair/{provider_id}")
    ResponseEntity<Provider> pairRequestWithProvider(@PathVariable String provider_id, @RequestParam String request_id){

        Optional<Provider> provider = providerService.getProviderById(provider_id);
        Optional<Request> request = requestService.getRequestById(request_id);
        if(request.isEmpty() || provider.isEmpty())
            return ResponseEntity.badRequest().build();

        request.get().setProvider(provider.get());

        requestService.updateRequest(request_id, request.get());
        return ResponseEntity.ok().build();
    }

    @PutMapping(value="/unpair")
    ResponseEntity<Provider> unpairRequestWithProvider(@RequestParam String request_id){

        Optional<Request> request = requestService.getRequestById(request_id);
        if(request.isEmpty())
            return ResponseEntity.badRequest().build();

        request.get().setProvider(null);

        requestService.updateRequest(request_id, request.get());
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    Request addRequest(@RequestBody Request request){
        return requestService.addRequest(request);
    }

    @DeleteMapping(value = "/{id}")
    void deleteRequestById(@PathVariable String id){
        requestService.deleteRequest(id);
    }


}
