package com.projet.repositories.impl;

import com.projet.core.impl.Repository;
import com.projet.entities.Article;
import com.projet.repositories.IArticleRepository;


public class ArticleRepository extends Repository<Article> implements IArticleRepository{
  

    public ArticleRepository() {
        super(Article.class);
       
    }

    @Override
    public void updateArticleQuantite(Article article) {
       update(article);
    }


}
