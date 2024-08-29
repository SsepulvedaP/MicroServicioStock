package com.MicroService.MicroServiceStock.application.handler.interfaces;

import com.MicroService.MicroServiceStock.application.dto.request.ArticleRequest;
import com.MicroService.MicroServiceStock.application.dto.response.ArticleResponse;


import java.util.List;
import java.util.Optional;

public interface IArticleHandler {
    void createArticle(ArticleRequest articleRequest);
    Optional<ArticleResponse> getArticleById(Long id);
    List<ArticleResponse> getAllArticles();
}
