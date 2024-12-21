package com.projet.repositories.impl;

import javax.persistence.TypedQuery;

import com.projet.core.impl.Repository;
import com.projet.entities.Client;
import com.projet.repositories.IClientRepository;

public class ClientRepository extends Repository<Client> implements IClientRepository {

    public ClientRepository() {
        super(Client.class);
    }

    @Override
    public Client findByTelephone(String telephone) {
        TypedQuery<Client> query = em.createNamedQuery("Client.findByTelephone", Client.class);
        query.setParameter("telephone", telephone);
        return query.getSingleResult();
    }

}
