package com.projet.services;

import java.util.List;


import com.projet.entities.Client;

public interface IClientService {
    void save(Client client);
    Client findById(int id);
    List<Client> findAll();
    Client chercherParTelephone(String telephone);
}
