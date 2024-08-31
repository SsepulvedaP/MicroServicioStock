package com.MicroService.MicroServiceStock.infrastructure.configuration;

import com.MicroService.MicroServiceStock.domain.api.IProductServicePort;
import com.MicroService.MicroServiceStock.domain.api.IBrandServicePort;
import com.MicroService.MicroServiceStock.domain.api.ICategoryServicePort;
import com.MicroService.MicroServiceStock.domain.spi.IProductPersistencePort;
import com.MicroService.MicroServiceStock.domain.spi.IBrandPersistencePort;
import com.MicroService.MicroServiceStock.domain.spi.ICategoryPersistencePort;
import com.MicroService.MicroServiceStock.domain.usecase.ProductUseCase;
import com.MicroService.MicroServiceStock.domain.usecase.BrandUseCase;
import com.MicroService.MicroServiceStock.domain.usecase.CategoryUseCase;
import com.MicroService.MicroServiceStock.infrastructure.jpa.adapter.ProductJpaAdapter;
import com.MicroService.MicroServiceStock.infrastructure.jpa.adapter.BrandJpaAdapter;
import com.MicroService.MicroServiceStock.infrastructure.jpa.adapter.CategoryJpaAdapter;
import com.MicroService.MicroServiceStock.infrastructure.jpa.mapper.ProductEntityMapper;
import com.MicroService.MicroServiceStock.infrastructure.jpa.mapper.BrandEntityMapper;
import com.MicroService.MicroServiceStock.infrastructure.jpa.mapper.CategoryEntityMapper;
import com.MicroService.MicroServiceStock.infrastructure.jpa.repository.IProductRepository;
import com.MicroService.MicroServiceStock.infrastructure.jpa.repository.IBrandRepository;
import com.MicroService.MicroServiceStock.infrastructure.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;
    private final IProductRepository iProductRepository;
    private final ProductEntityMapper productEntityMapper;


    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IBrandPersistencePort brandPersistencePort() {
        return new BrandJpaAdapter(brandRepository, brandEntityMapper);
    }

    @Bean
    public IBrandServicePort brandServicePort(){
        return new BrandUseCase(brandPersistencePort());
    }

    @Bean
    public IProductPersistencePort productPersistencePort() {
        return new ProductJpaAdapter(iProductRepository, productEntityMapper, brandRepository, categoryRepository);
    }

    @Bean
    public IProductServicePort productServicePort() {
        return new ProductUseCase(productPersistencePort());
    }
}
