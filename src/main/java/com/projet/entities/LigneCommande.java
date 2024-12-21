package com.projet.entities;

import javax.persistence.*;

@Entity
@Table(name = "lignes_commande")
public class LigneCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;
    
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
    
    private int quantite;
    private double prix;

    public LigneCommande(Article article2, int quantite2, double prix2) {
        this.article = article2;
        this.quantite = quantite2;
        this.prix = prix2;

    }
    public LigneCommande() {
       
    }
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    
    public Article getArticle() { return article; }
    public void setArticle(Article article) { this.article = article; }
    
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

     // Ajout de la méthode getTotal()
    public double getTotal() {
        return prix * quantite;
    }
    @Override
    public String toString() {
        return "LigneCommande [id=" + id + ", article=" + article + ", quantite=" + quantite + ", prix=" + prix + "]";
    }
}