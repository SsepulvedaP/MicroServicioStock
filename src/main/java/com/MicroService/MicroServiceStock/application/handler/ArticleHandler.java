package com.MicroService.MicroServiceStock.application.handler;

import com.MicroService.MicroServiceStock.application.dto.request.ArticleRequest;
import com.MicroService.MicroServiceStock.application.dto.response.ArticleResponse;
import com.MicroService.MicroServiceStock.application.handler.interfaces.IArticleHandler;
import com.MicroService.MicroServiceStock.application.mapper.request.ArticleRequestMapper;
import com.MicroService.MicroServiceStock.application.mapper.request.ArticleRequestMapperImpl;
import com.MicroService.MicroServiceStock.application.mapper.response.ArticleResponseMapper;
import com.MicroService.MicroServiceStock.domain.api.IArticleServicePort;
import com.MicroService.MicroServiceStock.domain.models.Article;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;
import com.MicroService.MicroServiceStock.infrastructure.exception.ArticleNotFoundException;
import com.MicroService.MicroServiceStock.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.MicroService.MicroServiceStock.utils.Constants.ARTICLE_NOT_FOUND;


@Service
@Transactional
@RequiredArgsConstructor
public class ArticleHandler implements IArticleHandler {

    private final IArticleServicePort articleServicePort;
    private final ArticleRequestMapper articleRequestMapper;
    private final ArticleResponseMapper articleResponseMapper;
    private final ArticleRequestMapperImpl articleRequestMapperImpl;

    @Override
    public void createArticle(ArticleRequest articleRequest) {
        Article article = articleRequestMapper.toArticle(articleRequest);
        articleServicePort.createArticle(article);
    }

    @Override
    public Optional<ArticleResponse> getArticleById(Long id) {
        return articleServicePort.getArticleById(id)
                .map(articleResponseMapper::toResponse);
    }

    @Override
    public List<ArticleResponse> getAllArticles() {
        return articleServicePort.getAllArticles().stream()
                .map(articleResponseMapper::toResponse)
                .toList();
    }


    @Override
    public PageCustom<ArticleResponse> getArticles(PageRequestCustom pageRequest, String brandName, String categoryName) {

        PageCustom<Article> articlesPage = articleServicePort.getArticlesByPage(pageRequest, brandName, categoryName);

        // Transformar a ArticleResponse
        List<ArticleResponse> articleResponses = articleResponseMapper.toResponseList(articlesPage.getContent());
        return new PageCustom<>(
                articleResponses,
                articlesPage.getTotalElements(),
                articlesPage.getTotalPages(),
                articlesPage.getCurrentPage(),
                articlesPage.isAscending()
        );
    }

}
