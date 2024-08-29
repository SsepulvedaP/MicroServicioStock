package com.MicroService.MicroServiceStock.infrastructure.jpa.mapper;

import com.MicroService.MicroServiceStock.domain.models.Article;
import com.MicroService.MicroServiceStock.infrastructure.jpa.entity.ArticleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BrandEntityMapper.class, CategoryEntityMapper.class})
public interface ArticleEntityMapper {
    @Mapping(target = "id", ignore = true)
    ArticleEntity toEntity(Article article);
    Article toArticle(ArticleEntity articleEntity);
    List<Article> toListArticle(List<ArticleEntity> articleEntityList);
}
