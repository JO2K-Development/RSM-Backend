package com.rsm.rsm_backend.controller;

import com.rsm.rsm_backend.entity.Provider;
import com.rsm.rsm_backend.entity.Request;
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


    public RequestController(RequestService requestService) {
        this.requestService = requestService;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    Request addRequest(@RequestBody Request request){
        return requestService.addRequest(request);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Request updateRequest(@PathVariable String id, @RequestBody Request updatedRequest){
        Optional<Request> existingRequest = requestService.getRequestById(id);
        if(existingRequest.isEmpty())
            throw new NoSuchElementException();

        requestService.updateRequest(id, updatedRequest);

        return updatedRequest;
    }

    @DeleteMapping(value = "/{id}")
    void deleteRequestById(@PathVariable String id){
        requestService.deleteRequest(id);
    }


}
