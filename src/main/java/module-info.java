module com.projet {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.hibernate.commons.annotations;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires org.yaml.snakeyaml;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
    requires java.base;

    opens com.projet to javafx.fxml;
    // Permet d'ouvrir le package com.projet.entities à Hibernate
    opens com.projet.entities to org.hibernate.orm.core;

    exports com.projet;
    exports com.projet.entities;
    exports com.projet.services;
    exports com.projet.repositories;
    exports com.projet.core;


}
