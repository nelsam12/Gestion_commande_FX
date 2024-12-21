package com.projet.core.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.projet.core.IRepository;

public class Repository <T> implements IRepository<T> {
    protected EntityManager em = null;
    private static EntityManagerFactory emf ;
    protected Class<T> entity;

    // Initialisation statique de l'EMF
    static {
        emf = Persistence.createEntityManagerFactory("mySQLUnit");
    }

    protected Repository(Class<T> entity) {
        this.entity = entity;
        if (em == null) {
            em = emf.createEntityManager();
        }
    }

    // Utiliser un EntityManager par opération
  

    @Override
    public T create(T entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return entity;

    }

    @Override
    public void delete(int id) {
        try {
            em.getTransaction().begin();
            T object = em.find(entity, id);
            if (object != null) {
                em.remove(object);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }

    @Override
    public List<T> findAll() {
        return em.createQuery("SELECT e FROM " + entity.getSimpleName() + " e", entity).getResultList();
    }

    @Override
    public T selectById(int id) {
        return selectBy("e.id", "id", id);
    }

    public T selectBy(String condition, String paramName, Object paramValue) {
        return em.createQuery("SELECT e FROM " + entity.getSimpleName() + " e WHERE " + condition, entity)
                .setParameter(paramName, paramValue)
                .getSingleResult();
    }

    @Override
    public void update(T entity) {
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }

}

