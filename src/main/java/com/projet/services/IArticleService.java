package com.projet.services;

import java.util.List;

import com.projet.entities.Article;

public interface IArticleService {
    void save(Article article);
    List<Article> getAllArticles();
}
