package com.projet.services.impl;

import java.util.List;

import com.projet.entities.Article;
import com.projet.repositories.IArticleRepository;
import com.projet.services.IArticleService;

public class ArticleService implements IArticleService {
    private final IArticleRepository articleRepository;

    public ArticleService(IArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAll() {
        return null;
    }

    public void updateQuantite(Long id, int quantite) {
       
    }

    @Override
    public void save(Article article) {
        articleRepository.create(article);
    }

    @Override
    public List<Article> getAllArticles() {
       return articleRepository.findAll();
    }
}
