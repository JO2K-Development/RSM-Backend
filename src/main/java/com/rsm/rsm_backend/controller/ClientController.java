package com.rsm.rsm_backend.controller;

import com.rsm.rsm_backend.entity.Client;
import com.rsm.rsm_backend.service.ClientService;
import org.springframework.http.MediaType;
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
    Client getClientById(@PathVariable String id){
        Optional<Client> client = clientService.getClientById(id);
        if(client.isEmpty())
            throw new NoSuchElementException();
        return client.get();
    }
    @GetMapping()
    List<Client> getAllClients(){
        return clientService.getAllClients();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    Client addClient(@RequestBody Client client){
        return clientService.addClient(client);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Client updateClient(@PathVariable String id, @RequestBody Client updatedClient){
        Optional<Client> existingClient = clientService.getClientById(id);
        if(existingClient.isEmpty())
            throw new NoSuchElementException();

        clientService.updateClient(id, updatedClient);

        return updatedClient;
    }

    @DeleteMapping(value = "/{id}")
    void deleteClientById(@PathVariable String id){
        clientService.deleteClient(id);
    }

}
