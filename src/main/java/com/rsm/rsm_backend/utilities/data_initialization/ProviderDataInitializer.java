package com.rsm.rsm_backend.utilities.data_initialization;

import com.rsm.rsm_backend.authenticationDTO.RegisterRequestDTO;
import com.rsm.rsm_backend.entity.Client;
import com.rsm.rsm_backend.entity.Provider;
import com.rsm.rsm_backend.entity.Request;
import com.rsm.rsm_backend.service.AuthenticationService;
import com.rsm.rsm_backend.service.ProviderService;
import com.rsm.rsm_backend.service.RequestService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProviderDataInitializer {
    private final AuthenticationService authenticationService;
    private final RequestService requestService;
    private final ProviderService providerService;

    @Autowired
    public ProviderDataInitializer(AuthenticationService authenticationService, RequestService requestService, ProviderService providerService) {
        this.authenticationService = authenticationService;
        this.requestService = requestService;
        this.providerService = providerService;
    }

    @PostConstruct
    public void init() {
        RegisterRequestDTO registerRequestDTO = RegisterRequestDTO
                .builder()
                .email("provider@gmail.com")
                .firstname("Marek")
                .lastname("Marucha")
                .password("password").build();

        authenticationService.register(registerRequestDTO);

        Client client1 = Client.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .phoneNumber("123456789")
                .email("jan.szyndlarewicz@gmail.com").build();

        Request request1 = Request
                .builder()
                .carMake("BMW")
                .carModel("E46")
                .carYear(2001)
                .creationDate(new java.sql.Date(System.currentTimeMillis()))
                .creator(client1)
                .message("Niedziela to dzień święty, a nie dzień na naprawę auta")
                .isVerified(true)
                .build();

        requestService.addRequest(request1);

        Client client2 = Client.builder()
                .firstName("Maciej")
                .lastName("Nowak")
                .phoneNumber("123456789")
                .email("maciej.nowak@gmail.com").build();

        Request request2 = Request
                .builder()
                .carMake("Audi")
                .carModel("TT")
                .carYear(2004)
                .creationDate(new java.sql.Date(System.currentTimeMillis()))
                .creator(client2)
                .message("Coś stuka, puka i trzeszczy")
                .isVerified(true)
                .build();

        requestService.addRequest(request2);

        Client client3 = Client.builder()
                .firstName("Robert")
                .lastName("Kubica")
                .phoneNumber("123456789")
                .email("robert.kubica@gmail.com").build();

        Request request3 = Request
                .builder()
                .carMake("Mercedes")
                .carModel("A 45")
                .carYear(2022)
                .creationDate(new java.sql.Date(System.currentTimeMillis()))
                .creator(client3)
                .message("Rozjebał się")
                .isVerified(true)
                .build();

        requestService.addRequest(request3);
        Provider provider = providerService.getProviderByEmail("provider@gmail.com").get();
        Request request = requestService.getAllRequests().get(2);
        request.setProvider(provider);
        requestService.updateRequest(request.getId(), request);

        Client client4 = Client.builder()
                .firstName("Robert")
                .lastName("Zklanu")
                .phoneNumber("123456789")
                .email("robert.bączylas@gmail.com").build();

        Request request4 = Request
                .builder()
                .carMake("Fiat")
                .carModel("500")
                .carYear(2056)
                .creationDate(new java.sql.Date(System.currentTimeMillis()))
                .creator(client4)
                .message("Przegląd")
                .isVerified(true)
                .build();

        requestService.addRequest(request4);
        provider = providerService.getProviderByEmail("provider@gmail.com").get();
        request = requestService.getAllRequests().get(3);
        request.setProvider(provider);
        requestService.updateRequest(request.getId(), request);
    }
}
