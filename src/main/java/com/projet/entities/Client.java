package com.projet.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "clients")
@NamedQuery(
    name = "Client.findByTelephone", 
    query = "SELECT c FROM Client c WHERE c.telephone = :telephone"
)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @Column(unique = true)
    private String telephone;

    private String ville;
    private String quartier;
    private String numeroVilla;

    @OneToMany(mappedBy = "client")
    private List<Commande> commandes = new ArrayList<>();

    public Client(String string, String string2, String telephone2, String string3, String string4, String string5) {
        // TODO Auto-generated constructor stub
    }

    public Client() {
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getNumeroVilla() {
        return numeroVilla;
    }

    public void setNumeroVilla(String numeroVilla) {
        this.numeroVilla = numeroVilla;
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    @Override
    public String toString() {
        return nom + " " + prenom;
    }

    public void addCommande(Commande commande) {
        if (commande != null)
            commandes.add(commande);
    }
}
