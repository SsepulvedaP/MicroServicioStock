package com.MicroService.MicroServiceStock.infrastructure.jpa.repository;

import com.MicroService.MicroServiceStock.infrastructure.jpa.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IArticleRepository extends JpaRepository<ArticleEntity, Long> {
    Optional<ArticleEntity> findByName(String name);
    Page<ArticleEntity> findByBrandNameContainingIgnoreCase(String brandName, Pageable pageable);
    Page<ArticleEntity> findByCategoriesNameContainingIgnoreCase(String categoryName, Pageable pageable);
    Page<ArticleEntity> findByBrandNameContainingIgnoreCaseAndCategoriesNameContainingIgnoreCase(String brandName, String categoryName, Pageable pageable);
}
