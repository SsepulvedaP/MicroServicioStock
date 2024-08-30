package com.MicroService.MicroServiceStock.domain.spi;

import com.MicroService.MicroServiceStock.domain.models.Article;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;

import java.util.List;
import java.util.Optional;

public interface IArticlePersistencePort {
    void createArticle(Article article);
    Optional<Article> getArticleById(Long id);
    List<Article> getAllArticles();
    PageCustom<Article> getArticlesByPage(PageRequestCustom pageRequest, String brandName, String categoryName);
}
