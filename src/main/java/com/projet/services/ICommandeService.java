package com.projet.services;

import com.projet.entities.Article;
import com.projet.entities.Commande;

public interface ICommandeService {
    void ajouterLigneCommande(Commande commande, Article article, int quantite, double prix);
    void save(Commande commande);
}
