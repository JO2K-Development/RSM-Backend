package com.rsm.rsm_backend.controller;

import com.rsm.rsm_backend.entity.Client;
import com.rsm.rsm_backend.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/client")
@CrossOrigin(origins = "http://localhost:3000")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Client> getClientById(@PathVariable String id) {
        Optional<Client> client = clientService.getClientById(id);
        if (client.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(client.get(), HttpStatus.OK);
    }

    @GetMapping()
    ResponseEntity<List<Client>> getAllClients() {
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Client> addClient(@RequestBody Client client) {
        return new ResponseEntity<>(clientService.addClient(client), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Client> updateClient(@PathVariable String id, @RequestBody Client updatedClient) {
        Optional<Client> existingClient = clientService.getClientById(id);
        if (existingClient.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        clientService.updateClient(id, updatedClient);

        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Client> deleteClientById(@PathVariable String id) {
        Optional<Client> client = clientService.getClientById(id);

        if (client.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        clientService.deleteClient(id);

        return new ResponseEntity<>(client.get(), HttpStatus.OK);
    }

}
