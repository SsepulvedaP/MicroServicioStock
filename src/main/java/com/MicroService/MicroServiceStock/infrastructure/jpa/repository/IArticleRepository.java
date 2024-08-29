package com.MicroService.MicroServiceStock.infrastructure.jpa.repository;

import com.MicroService.MicroServiceStock.infrastructure.jpa.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IArticleRepository extends JpaRepository<ArticleEntity, Long> {
    Optional<ArticleEntity> findByName(String name);
    void deleteByName(String name);
}
