package com.MicroService.MicroServiceStock.infrastructure.jpa.repository;

import com.MicroService.MicroServiceStock.infrastructure.jpa.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {
    Optional<BrandEntity> findByName(String name);
    void deleteByName(String name);
}
