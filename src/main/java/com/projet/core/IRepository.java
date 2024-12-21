package com.projet.core;

import java.util.List;

public interface IRepository <T> {
      // Crée un nouvel enregistrement
      T create(T entity);

      // Supprime un enregistrement par son identifiant
      void delete(int id);
  
      // Retourne tous les enregistrements
      List<T> findAll();
  
      // Sélectionne un enregistrement par son identifiant
      T selectById(int id);
  
      // Met à jour un enregistrement existant
      void update(T entity);
  
    
}
