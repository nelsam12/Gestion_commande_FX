package com.projet.views;

import com.projet.entities.Article;
import com.projet.entities.Client;
import com.projet.entities.Commande;
import com.projet.entities.LigneCommande;
import com.projet.services.IArticleService;
import com.projet.services.IClientService;
import com.projet.services.ICommandeService;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class CommandeView extends BorderPane {

    private final TextField tfTelephone = new TextField();
    private final Button btnRechercher = new Button("Rechercher");
    private final VBox clientInfoBox = new VBox(5);
    private final Label lblNomClient = new Label();
    private final Label lblAdresseClient = new Label();

    private final ComboBox<Article> cbArticles = new ComboBox<>();
    private final TextField tfQuantite = new TextField();
    private final TextField tfPrix = new TextField();
    private final Button btnAjouter = new Button("Ajouter à la commande");

    private final TableView<LigneCommande> tableLignes = new TableView<>();

    private final Button btnValider = new Button("Valider");

    private final Label lblTotal = new Label("Total: 0.00 €");

    private Client clientActuel;
    private final ObservableList<LigneCommande> lignesCommande = FXCollections.observableArrayList();
    private final ObservableList<Article> articles = FXCollections.observableArrayList();

    private ICommandeService commandeService;
    private IArticleService articleService;
    private IClientService clientService;
    private Commande commande = null;

    public CommandeView(ICommandeService commandeService,
            IArticleService articleService,
            IClientService clientService) {
        this.commandeService = commandeService;
        this.articleService = articleService;
        this.clientService = clientService;
        initialiserComposants();
        setupLayout();
        configurerEvenements();
        chargerDonneesArticles();
        appliquerStyles();
    }

    private void appliquerStyles() {
        // Appliquer les styles aux composants

        // Root
        this.setStyle("-fx-font-family: 'Segoe UI', Arial, sans-serif;" +
                "-fx-background-color: #f4f4f4;" +
                "-fx-padding: 10;");

        // TextField
        tfTelephone.setStyle("-fx-padding: 8;");
        tfQuantite.setStyle("-fx-padding: 8;");
        tfPrix.setStyle("-fx-padding: 8;");

        // Button
        btnRechercher
                .setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 8 16; -fx-cursor: hand;");
        btnRechercher.setOnMouseEntered(e -> btnRechercher
                .setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; -fx-padding: 8 16; -fx-cursor: hand;"));
        btnRechercher.setOnMouseExited(e -> btnRechercher
                .setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 8 16; -fx-cursor: hand;"));

        btnAjouter
                .setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 8 16; -fx-cursor: hand;");
        btnAjouter.setOnMouseEntered(e -> btnAjouter
                .setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; -fx-padding: 8 16; -fx-cursor: hand;"));
        btnAjouter.setOnMouseExited(e -> btnAjouter
                .setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 8 16; -fx-cursor: hand;"));

        btnValider
                .setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-padding: 8 16; -fx-cursor: hand;");

        // TableView
        tableLignes.setStyle("-fx-background-color: white; -fx-background-radius: 4;");
        tableLignes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Label
        lblTotal.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        lblNomClient.setStyle("-fx-font-size: 14px;");
        lblAdresseClient.setStyle("-fx-font-size: 14px;");
    }

    private void initialiserComposants() {
        tfTelephone.setPromptText("Numéro de téléphone");
        tfQuantite.setPromptText("Quantité");
        tfPrix.setPromptText("Prix unitaire");

        cbArticles.setItems(articles);
        cbArticles.setPromptText("Sélectionner un article");
        // Colonne pour les actions (modifier et supprimer)
        TableColumn<LigneCommande, String> colArticle = new TableColumn<>("Article");
        colArticle
                .setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getArticle().getDesignation()));

        TableColumn<LigneCommande, Integer> colQuantite = new TableColumn<>("Quantité");
        colQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        TableColumn<LigneCommande, Double> colTotal = new TableColumn<>("Total");

        TableColumn<LigneCommande, Double> colPrix = new TableColumn<>("Prix unitaire");
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));


        
       
        colTotal.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getTotal()).asObject());
        TableColumn<LigneCommande, Void> colActions = new TableColumn<>("Actions");
        // Créer une cellule avec les boutons Modifier et Supprimer
        colActions.setCellFactory(param -> new TableCell<LigneCommande, Void>() {
            private final Button btnModifier = new Button("Modifier");
            private final Button btnSupprimer = new Button("Supprimer");

            {
                // Modifier le bouton
                btnModifier.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                btnModifier.setOnAction(e -> modifierLigneCommande(getTableRow().getIndex()));

                // Supprimer le bouton
                btnSupprimer.setStyle("-fx-background-color: #FF3D00; -fx-text-fill: white;");
                btnSupprimer.setOnAction(e -> supprimerLigneCommande(getTableRow().getIndex()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10);
                    hbox.getChildren().addAll(btnModifier, btnSupprimer);
                    setGraphic(hbox);
                }
            }
        });

        tableLignes.getColumns().addAll(colArticle, colQuantite, colPrix, colTotal, colActions);
        tableLignes.setItems(lignesCommande);

        setCommandeSectionDisabled(true);
    }

    protected void supprimerLigneCommande(int index) {
        // Vérifier si l'index est valide
        if (index >= 0 && index < lignesCommande.size()) {
            // Récupérer la ligne à supprimer
            LigneCommande ligne = lignesCommande.get(index);

            // Rétablir la quantité disponible de l'article
            Article article = ligne.getArticle();
            article.setQuantiteDisponible(article.getQuantiteDisponible() + ligne.getQuantite());

            // Supprimer la ligne de la liste
            lignesCommande.remove(index);

            // Supprimer la ligne de la commande
            commande.removeLigneCommande(ligne);

            // Actualiser le total
            actualiserTotal();

            // Optionnel : Afficher un message de confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ligne supprimée");
            alert.setContentText("La ligne de commande a été supprimée avec succès.");
            alert.showAndWait();
        }
    }

    protected void modifierLigneCommande(int index) {
        // Vérifier si l'index est valide
        if (index >= 0 && index < lignesCommande.size()) {
            // Récupérer la ligne à modifier
            LigneCommande ligne = lignesCommande.get(index);

            // Créer un formulaire ou une boîte de dialogue pour modifier la ligne
            // Par exemple, vous pouvez modifier la quantité ou le prix
            TextInputDialog dialog = new TextInputDialog(String.valueOf(ligne.getQuantite()));
            dialog.setTitle("Modifier la ligne");
            dialog.setHeaderText("Modifier la quantité");
            dialog.setContentText("Nouvelle quantité :");

            dialog.showAndWait().ifPresent(newQuantite -> {
                try {
                    // Convertir la quantité en entier
                    int quantite = Integer.parseInt(newQuantite);

                    if (quantite > 0 && quantite <= ligne.getArticle().getQuantiteDisponible() + ligne.getQuantite()) {
                        // Mettre à jour la quantité de la ligne et réajuster l'article
                        int difference = quantite - ligne.getQuantite();
                        ligne.setQuantite(quantite);

                        // Réajuster la quantité disponible de l'article
                        ligne.getArticle()
                                .setQuantiteDisponible(ligne.getArticle().getQuantiteDisponible() - difference);

                        // Actualiser le total de la ligne

                        actualiserTotal();

                        // Forcer la mise à jour de la vue pour cette ligne spécifique
                        tableLignes.refresh();

                        // Optionnel : Afficher un message de confirmation
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Ligne modifiée");
                        alert.setContentText("La ligne de commande a été modifiée avec succès.");
                        alert.showAndWait();
                    } else {
                        // Si la quantité est invalide, afficher un message d'erreur
                        afficherErreur("Quantité invalide",
                                "La quantité doit être positive et ne pas dépasser le stock disponible.");
                    }
                } catch (NumberFormatException e) {
                    // Si l'utilisateur saisit une valeur non valide
                    afficherErreur("Erreur de saisie", "Veuillez entrer une quantité valide.");
                }
            });
        }
    }

    private void setupLayout() {
        HBox searchBox = new HBox(10);
        searchBox.getChildren().addAll(new Label("Téléphone:"), tfTelephone, btnRechercher);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        VBox clientSection = new VBox(10);
        clientSection.getChildren().addAll(searchBox, clientInfoBox);
        clientSection.setPadding(new Insets(10));
        setTop(clientSection);

        GridPane articleGrid = new GridPane();
        articleGrid.setHgap(10);
        articleGrid.setVgap(10);
        articleGrid.addRow(0, new Label("Article:"), cbArticles);
        articleGrid.addRow(1, new Label("Quantité:"), tfQuantite);
        articleGrid.addRow(2, new Label("Prix:"), tfPrix);
        articleGrid.add(btnAjouter, 1, 3);

        VBox centerBox = new VBox(10);
        centerBox.getChildren().addAll(articleGrid, tableLignes);
        centerBox.setPadding(new Insets(10));
        setCenter(centerBox);

        HBox bottomBox = new HBox(10);
        bottomBox.getChildren().addAll(btnValider, lblTotal);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(10));
        setBottom(bottomBox);
    }

    private void configurerEvenements() {
        btnRechercher.setOnAction(e -> rechercherClient());
        btnAjouter.setOnAction(e -> ajouterLigneCommande());
        btnValider.setOnAction(e -> validerCommande());

        cbArticles.setOnAction(e -> {
            Article article = cbArticles.getValue();
            if (article != null) {
                tfPrix.setText(String.valueOf(article.getPrix()));
            }
        });

        lignesCommande.addListener((ListChangeListener<LigneCommande>) c -> actualiserTotal());
    }

    private void validerCommande() {
        System.out.println("Commande validée");
        // Vérifier si une commande est en cours
        if (clientActuel != null && commande != null) {

            System.out.println(commande);
            if (!commande.getLignes().isEmpty()) {
                // Calculer le total de la commande
                double total = lignesCommande.stream().mapToDouble(LigneCommande::getTotal).sum();
                commande.setTotal(total); // Assurez-vous que la commande a un attribut `total` pour stocker le total de
                                          // la commande.
                commandeService.save(commande); // Sauvegarder la commande validée dans la base de données
                for (LigneCommande ligneCommande : commande.getLignes()) {
                    articleService.updateArticleQuantite(ligneCommande.getArticle());
                }

                // Vous pouvez aussi enregistrer les lignes de commande associées si nécessaire
                for (LigneCommande ligne : lignesCommande) {
                    System.out.println(ligne);
                }

                // Afficher un message de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Commande validée");
                alert.setContentText("La commande a été validée et enregistrée avec succès.");
                alert.showAndWait();

                // Désactiver les champs après validation
                setCommandeSectionDisabled(true);
                commande = null;
                // Rénitialiser la vue
                tfTelephone.clear();
                cbArticles.setValue(null);
                tfQuantite.clear();
                tfPrix.clear();
                lignesCommande.clear();
                lblTotal.setText("Total: 0.00");
            } else {
                afficherErreur("Erreur", "La commande doit contenir au moins une ligne.");
            }
        } else {
            afficherErreur("Erreur", "Aucune commande en cours.");
        }
    }

    private void rechercherClient() {
        String telephone = tfTelephone.getText();
        if (telephone.isEmpty()) {
            // Afficher une erreur si le champ de recherche est vide
            afficherErreur("Champ requis", "Veuillez saisir un numéro de téléphone.");
            return;
        }
        clientActuel = clientService.chercherParTelephone(telephone);
        System.out.println(clientActuel);
        if (clientActuel != null) {
            afficherInfoClient();
            // Si le client existe déjà, récupérer la commande active ou en créer une
            // nouvelle
            commande = new Commande();
            commande.setClient(clientActuel); // Associer la commande au client
            clientActuel.addCommande(commande); // Ajouter la commande à la liste des commandes du client
            // commandeService.save(commande); // Sauvegarder la nouvelle commande

            setCommandeSectionDisabled(false);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Client non trouvé");
            alert.setContentText("Aucun client trouvé avec ce numéro.");
            alert.showAndWait();
            setCommandeSectionDisabled(true);
        }
    }

    private void afficherInfoClient() {
        lblNomClient.setText(clientActuel.getNom() + " " + clientActuel.getPrenom());
        lblAdresseClient.setText(clientActuel.getVille() + ", " + clientActuel.getQuartier() + ", Villa "
                + clientActuel.getNumeroVilla());
        clientInfoBox.getChildren().clear();
        clientInfoBox.getChildren().addAll(lblNomClient, lblAdresseClient);
    }

    private void ajouterLigneCommande() {
        Article article = cbArticles.getValue();
        try {
            int quantite = Integer.parseInt(tfQuantite.getText());
            double prix = Double.parseDouble(tfPrix.getText());

            if (article != null && quantite > 0 && prix > 0) {
                // Vérifier si l'article est déjà présent dans la commande
                boolean articleExistant = false;
                for (LigneCommande ligne : lignesCommande) {
                    if (ligne.getArticle().equals(article)) {
                        // Si l'article existe déjà, on met à jour la quantité
                        ligne.setQuantite(ligne.getQuantite() + quantite); // Ajouter la quantité à l'existante
                        articleExistant = true;
                        break;
                    }
                }

                if (!articleExistant) {
                    // Si l'article n'est pas encore dans la commande, on l'ajoute
                    if (quantite <= article.getQuantiteDisponible()) {
                        LigneCommande ligne = new LigneCommande(article, quantite, prix);
                        lignesCommande.add(ligne);
                        article.setQuantiteDisponible(article.getQuantiteDisponible() - quantite); // Mettre à jour la
                                                                                                   // quantité
                                                                                                   // disponible de
                                                                                                   // l'article
                        commande.addLigneCommande(ligne); // Ajouter la ligne de commande à la commande
                        viderChampsArticle(); // Réinitialiser les champs
                    } else {
                        afficherErreur("Quantité non disponible", "La quantité demandée dépasse le stock disponible.");
                    }
                } else {
                    // Si l'article a été mis à jour, afficher un message
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Quantité mise à jour");
                    alert.setContentText("La quantité de l'article a été mise à jour.");
                    alert.showAndWait();
                }
                 // Forcer la mise à jour de la vue pour cette ligne spécifique
                 tableLignes.refresh();
            }
        } catch (NumberFormatException e) {
            afficherErreur("Erreur de saisie", "Veuillez vérifier les valeurs saisies.");
        }
    }

    private void chargerDonneesArticles() {
        // Charger les articles depuis la base de données via le service
        articles.setAll(articleService.getAllArticles());
    }

    private void actualiserTotal() {
        double total = lignesCommande.stream().mapToDouble(LigneCommande::getTotal).sum();
        lblTotal.setText(String.format("Total: %.2f €", total));
    }

    private void setCommandeSectionDisabled(boolean disabled) {
        cbArticles.setDisable(disabled);
        tfQuantite.setDisable(disabled);
        tfPrix.setDisable(disabled);
        btnAjouter.setDisable(disabled);
        tableLignes.setDisable(disabled);

        btnValider.setDisable(disabled);
    }

    private void viderChampsArticle() {
        cbArticles.setValue(null);
        tfQuantite.clear();
        tfPrix.clear();
    }

    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
