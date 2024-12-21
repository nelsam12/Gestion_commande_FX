package com.projet.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommande> lignes = new ArrayList<>();

    private double total;

    

    public Commande() {
        date  = LocalDate.now();
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal2() {
        return total;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<LigneCommande> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommande> lignes) {
        this.lignes = lignes;
    }

    public double getTotal() {
        return lignes.stream()
                .mapToDouble(ligne -> ligne.getPrix() * ligne.getQuantite())
                .sum();
    }

    // Méthode pour ajouter une ligne de commande
    public void addLigneCommande(LigneCommande ligne) {
        if (ligne != null) {
            lignes.add(ligne);
            ligne.setCommande(this);
        }
    }

    @Override
    public String toString() {
        return "Commande [id=" + id + ", date=" + date + ", total=" + total + "]";
    }
}
