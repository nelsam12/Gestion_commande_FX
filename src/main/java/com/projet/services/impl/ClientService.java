package com.projet.services.impl;

import java.util.List;

import com.projet.entities.Client;
import com.projet.repositories.IClientRepository;
import com.projet.services.IClientService;

public class ClientService implements IClientService{

    private IClientRepository clientRepository ;

    public ClientService(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void save(Client client) {
        clientRepository.create(client);
    }

    @Override
    public Client findById(int id) {
        return null;
    }
    
    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client chercherParTelephone(String telephone) {
        return clientRepository.findByTelephone(telephone);
    }
    
}
