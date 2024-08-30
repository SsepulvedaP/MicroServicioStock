package com.MicroService.MicroServiceStock.application.handler.interfaces;

import com.MicroService.MicroServiceStock.application.dto.request.ArticleRequest;
import com.MicroService.MicroServiceStock.application.dto.response.ArticleResponse;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;


import java.util.List;
import java.util.Optional;

public interface IArticleHandler {
    void createArticle(ArticleRequest articleRequest);
    Optional<ArticleResponse> getArticleById(Long id);
    List<ArticleResponse> getAllArticles();
    PageCustom<ArticleResponse> getArticles(PageRequestCustom pageRequest, String brandName, String categoryName);
}
