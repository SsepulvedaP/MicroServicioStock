package com.MicroService.MicroServiceStock.infrastructure.jpa.repository;

import com.MicroService.MicroServiceStock.infrastructure.jpa.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByName(String name);
    Page<ProductEntity> findByBrandNameContainingIgnoreCase(String brandName, Pageable pageable);
    Page<ProductEntity> findByCategoriesNameContainingIgnoreCase(String categoryName, Pageable pageable);
    Page<ProductEntity> findByBrandNameContainingIgnoreCaseAndCategoriesNameContainingIgnoreCase(String brandName, String categoryName, Pageable pageable);
}
