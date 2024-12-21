package com.projet;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.projet.repositories.IArticleRepository;
import com.projet.repositories.IClientRepository;
import com.projet.repositories.ICommandeRepository;
import com.projet.repositories.impl.ArticleRepository;
import com.projet.repositories.impl.ClientRepository;
import com.projet.repositories.impl.CommandeRepository;
import com.projet.services.IArticleService;
import com.projet.services.IClientService;
import com.projet.services.ICommandeService;
import com.projet.services.impl.ArticleService;
import com.projet.services.impl.ClientService;
import com.projet.services.impl.CommandeService;
import com.projet.views.CommandeView;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {


        // Repository
        IClientRepository clientRepository = new ClientRepository();
        IArticleRepository articleRepository = new ArticleRepository();
        ICommandeRepository commandeRepository = new CommandeRepository();
        //Service
        IArticleService articleService = new ArticleService(articleRepository);  // Récupérer les services via Spring (ou autre framework)
        IClientService clientService = new ClientService(clientRepository);
        ICommandeService commandeService = new CommandeService(commandeRepository, clientRepository, articleRepository);

        CommandeView root = new CommandeView(commandeService, articleService, clientService);
        Scene scene = new Scene(root, 900, 600);
      // Appliquer le CSS à la scène
    //   scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        
        
        primaryStage.setTitle("Gestion des Commandes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}