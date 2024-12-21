package com.projet.services.impl;

import com.projet.entities.Article;
import com.projet.entities.Client;
import com.projet.entities.Commande;
import com.projet.entities.LigneCommande;
import com.projet.repositories.IArticleRepository;
import com.projet.repositories.IClientRepository;
import com.projet.repositories.ICommandeRepository;
import com.projet.services.ICommandeService;

public class CommandeService  implements ICommandeService{
    private ICommandeRepository commandeRepo;
    private IClientRepository clientRepo;
    private IArticleRepository articleRepo;
    
    public CommandeService() {
    }

    public CommandeService(ICommandeRepository commandeRepo, IClientRepository clientRepo,
            IArticleRepository articleRepo) {
        this.commandeRepo = commandeRepo;
        this.clientRepo = clientRepo;
        this.articleRepo = articleRepo;
    }

    public Client rechercherClientParTelephone(String telephone) {
        return clientRepo.findByTelephone(telephone);
    }
    
    public void ajouterLigneCommande(Commande commande, Article article, int quantite, double prix) {
        // Vérification de la disponibilité
        if (article.getQuantiteDisponible() >= quantite) {
            LigneCommande ligne = new LigneCommande();
            ligne.setArticle(article);
            ligne.setQuantite(quantite);
            ligne.setPrix(prix);
            commande.getLignes().add(ligne);
        }
    }

    @Override
    public void save(Commande commande) {
        commandeRepo.create(commande);
    }

    
}
