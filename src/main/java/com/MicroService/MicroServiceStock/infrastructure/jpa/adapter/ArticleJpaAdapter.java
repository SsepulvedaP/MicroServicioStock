package com.MicroService.MicroServiceStock.infrastructure.jpa.adapter;

import com.MicroService.MicroServiceStock.domain.exceptions.DuplicateArticleNameException;
import com.MicroService.MicroServiceStock.domain.models.Article;
import com.MicroService.MicroServiceStock.domain.spi.IArticlePersistencePort;
import com.MicroService.MicroServiceStock.infrastructure.exception.NoDataFoundException;
import com.MicroService.MicroServiceStock.infrastructure.jpa.entity.ArticleEntity;
import com.MicroService.MicroServiceStock.infrastructure.jpa.mapper.ArticleEntityMapper;
import com.MicroService.MicroServiceStock.infrastructure.jpa.repository.IArticleRepository;
import com.MicroService.MicroServiceStock.infrastructure.jpa.repository.IBrandRepository;
import com.MicroService.MicroServiceStock.infrastructure.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class ArticleJpaAdapter implements IArticlePersistencePort {

    private final IArticleRepository articleRepository;
    private final ArticleEntityMapper articleEntityMapper;
    private final IBrandRepository brandRepository;
    private final ICategoryRepository categoryRepository;

    @Override
    public void createArticle(Article article) {
        if(articleRepository.findByName(article.getName()).isPresent()){
            throw new DuplicateArticleNameException(article.getName());
        }

       ArticleEntity articleEntity = articleEntityMapper.toEntity(article);
        articleRepository.save(articleEntity);
    }

    @Override
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id)
                .map(articleEntityMapper::toArticle);
    }

    @Override
    public List<Article> getAllArticles() {
        List <ArticleEntity> articleEntityList = articleRepository.findAll();
        if(articleEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return articleEntityMapper.toListArticle(articleEntityList);
    }
}
