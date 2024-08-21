package com.MicroService.MicroServiceStock.infrastructure.configuration;

import com.MicroService.MicroServiceStock.domain.api.ICategoryServicePort;
import com.MicroService.MicroServiceStock.domain.spi.ICategoryPersistencePort;
import com.MicroService.MicroServiceStock.domain.usecase.CategoryUseCase;
import com.MicroService.MicroServiceStock.infrastructure.jpa.adapter.CategoryJpaAdapter;
import com.MicroService.MicroServiceStock.infrastructure.jpa.mapper.CategoryEntityMapper;
import com.MicroService.MicroServiceStock.infrastructure.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }
}
