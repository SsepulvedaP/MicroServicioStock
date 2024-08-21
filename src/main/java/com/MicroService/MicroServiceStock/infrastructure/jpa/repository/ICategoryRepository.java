package com.MicroService.MicroServiceStock.infrastructure.jpa.repository;


import com.MicroService.MicroServiceStock.infrastructure.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository <CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);
    //Delete
    void deleteByName(String name);

}
