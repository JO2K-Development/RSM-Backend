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
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Client> getClientById(@PathVariable String id){
        Optional<Client> client = clientService.getClientById(id);
        if(client.isEmpty())
            throw new NoSuchElementException();

        return new ResponseEntity<>(client.get(), HttpStatus.OK);
    }
    @GetMapping()
    ResponseEntity<List<Client>> getAllClients(){
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Client> addClient(@RequestBody Client client){
        return new ResponseEntity<>(clientService.addClient(client), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Client> updateClient(@PathVariable String id, @RequestBody Client updatedClient){
        Optional<Client> existingClient = clientService.getClientById(id);
        if(existingClient.isEmpty())
            throw new NoSuchElementException();

        clientService.updateClient(id, updatedClient);

        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    void deleteClientById(@PathVariable String id){
        clientService.deleteClient(id);
    }

}
