package com.projet.repositories.impl;

import com.projet.core.impl.Repository;
import com.projet.entities.Commande;
import com.projet.repositories.ICommandeRepository;

public class CommandeRepository extends Repository<Commande> implements ICommandeRepository {
   
    public CommandeRepository() {
        super(Commande.class);
    }

   
}
