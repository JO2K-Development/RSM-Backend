package com.rsm.rsm_backend.service;

import com.rsm.rsm_backend.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> getAllClients();
    Optional<Client> getClientById(String id);
    Client addClient(Client client);
    Optional<Client> updateClient(String id, Client updatedClient);
    void deleteClient(String id);

}
