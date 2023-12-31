package com.rsm.rsm_backend.controller;

import com.rsm.rsm_backend.DTO.RequestStatusDTO;
import com.rsm.rsm_backend.entity.Provider;
import com.rsm.rsm_backend.entity.Request;
import com.rsm.rsm_backend.entity.RequestStatus;
import com.rsm.rsm_backend.entity.VerificationToken;
import com.rsm.rsm_backend.service.EmailService;
import com.rsm.rsm_backend.service.ProviderService;
import com.rsm.rsm_backend.service.RequestService;
import com.rsm.rsm_backend.service.VerificationTokenService;
import com.rsm.rsm_backend.utilities.email.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.rsm.rsm_backend.service.EmailService.buildEmail;

@RestController
@RequestMapping("/api/v1/request")
@CrossOrigin
public class RequestController {

    private final RequestService requestService;
    private final ProviderService providerService;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;
    private final EmailValidator emailValidator;
    private final ModelMapper modelMapper;


    public RequestController(RequestService requestService, ProviderService providerService, EmailService emailService, VerificationTokenService verificationTokenService, EmailValidator emailValidator, ModelMapper modelMapper) {
        this.requestService = requestService;
        this.providerService = providerService;
        this.emailService = emailService;
        this.verificationTokenService = verificationTokenService;
        this.emailValidator = emailValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Request> getRequestById(@PathVariable String id) {
        Optional<Request> request = requestService.getRequestById(id);

        return request.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping()
    ResponseEntity<List<Request>> getAllRequests() {
        return new ResponseEntity<>(requestService.getAllRequests(), HttpStatus.OK);
    }

    @GetMapping(value = "/confirm")
    ResponseEntity<Request> confirm(@RequestParam("token") String token) {
        Optional<VerificationToken> verificationToken = verificationTokenService.getToken(token);
        if (verificationToken.isPresent()) {
            if (verificationToken.get().isExpired())
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

            Request request = verificationToken.get().getRequest();
            request.setRequestStatus(RequestStatus.WAITING_FOR_A_MECHANIC_ASSIGNMENT);
            request.setIsVerified(true);
            requestService.updateRequest(request.getId(), request);

            Optional<Request> requestOptional = requestService.getRequestById(request.getId());
            requestOptional.ifPresent(value -> value.setCreationDate(new Date(System.currentTimeMillis())));

        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(value = "/assigned/{email}")
    ResponseEntity<List<Request>> getAllRequestsOfProvider(@PathVariable String email) {
        if (providerService.getProviderByEmail(email).isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(requestService.getRequestsByProviderEmail(email), HttpStatus.OK);
    }

    @GetMapping(value = "/notassigned")
    ResponseEntity<List<Request>> getAllRequestsNotAssigned() {
        return new ResponseEntity<>(requestService.getVerifiedOrDoneRequestsWithoutProvider(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Request> updateRequest(@PathVariable String id, @RequestBody Request updatedRequest) {

        Optional<Request> existingRequest = requestService.getRequestById(id);
        if (existingRequest.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(requestService.updateRequest(id, updatedRequest), HttpStatus.OK);
    }

    @PutMapping(value = "/update-status/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Request> updateStatusRequest(@PathVariable String id, @RequestBody RequestStatusDTO requestStatusDTO) {

        Optional<Request> existingRequest = requestService.getRequestById(id);
        if (existingRequest.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Request existingRequestValue;

        modelMapper.map(requestStatusDTO, existingRequestValue = existingRequest.get());
        System.out.println(existingRequestValue.getRequestStatus());
        System.out.println(existingRequestValue.getPickupDate());
        System.out.println(existingRequestValue.getDeliveryDate());

//        existingRequestValue.setRequestStatus(requestStatusDTO.requestStatus());
//        existingRequestValue.setPickupDate(requestStatusDTO.pickupDate());
//        existingRequestValue.setDeliveryDate(requestStatusDTO.deliveryDate());
//        existingRequestValue.setDiagnosis(requestStatusDTO.diagnosis());

        return new ResponseEntity<>(requestService.updateRequest(id, existingRequestValue), HttpStatus.OK);
    }

    @PutMapping(value = "/pair/{request_id}")
    ResponseEntity<Request> pairRequestWithProvider(@PathVariable String request_id, @RequestParam String provider_id) {

        Optional<Provider> provider = providerService.getProviderById(provider_id);
        Optional<Request> request = requestService.getRequestById(request_id);
        if (request.isEmpty() || provider.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        request.get().setProvider(provider.get());
        request.get().setRequestStatus(RequestStatus.WAITING_FOR_DATE_ASSIGNMENT);

        return new ResponseEntity<>(requestService.updateRequest(request_id, request.get()), HttpStatus.OK);
    }

    @PutMapping(value = "/unpair/{id}")
    ResponseEntity<Request> unpairRequestWithProvider(@PathVariable String id) {

        Optional<Request> request = requestService.getRequestById(id);
        if (request.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        request.get().setProvider(null);
        request.get().setRequestStatus(RequestStatus.WAITING_FOR_A_MECHANIC_ASSIGNMENT);

        return new ResponseEntity<>(requestService.updateRequest(id, request.get()), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Request> addRequest(@RequestBody Request request) {
        request.setRequestStatus(RequestStatus.WAITING_FOR_AN_EMAIL_VERIFICATION);

        boolean isValidEmail = emailValidator.test(request.getCreator().getEmail());
        if (!isValidEmail)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        Random random = new Random();
        int EXPIRATION_TIME = 1000 * 60 * 15;
        VerificationToken verificationToken = VerificationToken.
                builder().
                token(String.valueOf(random.nextLong()) + random.nextLong()).
                request(request).
                creationTime(new Date(System.currentTimeMillis())).
                expirationTime(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).
                build();

        verificationTokenService.addVerificationToken(verificationToken);
        request.setIsVerified(false);
        //TODO local host has to be changed
        String link = "http://localhost:8080/api/v1/request/confirm?token=" + verificationToken.getToken();
        emailService.sendEmail(request.getCreator().getEmail(), buildEmail(request.getCreator().getFirstName(), link));
        return new ResponseEntity<>(requestService.addRequest(request), HttpStatus.OK);
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
