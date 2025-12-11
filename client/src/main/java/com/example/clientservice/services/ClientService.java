package com.example.clientservice.services;


import com.example.clientservice.entities.Client;
import com.example.clientservice.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long clientId) throws Exception {
        return findClientOrThrow(clientId);
    }

    public Client saveClient(Client client) {
        validateClient(client);
        return clientRepository.save(client);
    }

    private Client findClientOrThrow(Long clientId) throws Exception {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isEmpty()) {
            throw new Exception("Impossible de trouver le client avec l'identifiant: " + clientId);
        }
        return clientOptional.get();
    }

    private void validateClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Le client ne peut pas être null");
        }
        if (client.getNom() == null || client.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du client est obligatoire");
        }
    }
}