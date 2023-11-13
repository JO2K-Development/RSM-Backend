package com.rsm.rsm_backend.service.impl;

import com.rsm.rsm_backend.entity.Client;
import com.rsm.rsm_backend.repository.ClientRepository;
import com.rsm.rsm_backend.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClientById(String id) {
        return clientRepository.findById(id);

    }

    @Override
    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(String id, Client updatedClient) {
        updatedClient.setId(id);

        return clientRepository.save(updatedClient);

    }

    @Override
    public void deleteClient(String id) {
         clientRepository.deleteById(id);
    }
}
