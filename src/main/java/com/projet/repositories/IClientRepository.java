package com.projet.repositories;

import com.projet.core.IRepository;
import com.projet.entities.Client;

public interface IClientRepository extends IRepository<Client> {

    Client findByTelephone(String telephone);
    
}
