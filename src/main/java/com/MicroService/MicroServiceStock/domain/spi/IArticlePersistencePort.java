package com.MicroService.MicroServiceStock.domain.spi;

import com.MicroService.MicroServiceStock.domain.models.Article;

import java.util.List;
import java.util.Optional;

public interface IArticlePersistencePort {
    void createArticle(Article article);
    Optional<Article> getArticleById(Long id);
    List<Article> getAllArticles();
}
