package com.MicroService.MicroServiceStock.infrastructure.jpa.adapter;

import com.MicroService.MicroServiceStock.domain.exceptions.DuplicateArticleNameException;
import com.MicroService.MicroServiceStock.domain.models.Product;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;
import com.MicroService.MicroServiceStock.domain.spi.IProductPersistencePort;
import com.MicroService.MicroServiceStock.infrastructure.exception.NoDataFoundException;
import com.MicroService.MicroServiceStock.infrastructure.jpa.entity.ProductEntity;
import com.MicroService.MicroServiceStock.infrastructure.jpa.mapper.ProductEntityMapper;
import com.MicroService.MicroServiceStock.infrastructure.jpa.repository.IProductRepository;
import com.MicroService.MicroServiceStock.infrastructure.jpa.repository.IBrandRepository;
import com.MicroService.MicroServiceStock.infrastructure.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class ProductJpaAdapter implements IProductPersistencePort {

    private final IProductRepository articleRepository;
    private final ProductEntityMapper productEntityMapper;
    private final IBrandRepository brandRepository;
    private final ICategoryRepository categoryRepository;

    @Override
    public void createProduct(Product product) {
        if(articleRepository.findByName(product.getName()).isPresent()){
            throw new DuplicateArticleNameException(product.getName());
        }

       ProductEntity productEntity = productEntityMapper.toEntity(product);
        articleRepository.save(productEntity);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return articleRepository.findById(id)
                .map(productEntityMapper::toProduct);
    }

    @Override
    public List<Product> getAllProducts() {
        List <ProductEntity> productEntityList = articleRepository.findAll();
        if(productEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return productEntityMapper.toListProduct(productEntityList);
    }

    @Override
    public PageCustom<Product> getProductsByPage(PageRequestCustom pageRequest, String brandName, String categoryName) {
        Sort sort = Sort.by(pageRequest.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC, pageRequest.getSortField());
        Pageable pageable = PageRequest.of(pageRequest.getPage(), pageRequest.getSize(), sort);

        Page<ProductEntity> pageResult;

        if (brandName != null && !brandName.isEmpty() && categoryName != null && !categoryName.isEmpty()) {
            pageResult = articleRepository.findByBrandNameContainingIgnoreCaseAndCategoriesNameContainingIgnoreCase(brandName, categoryName, pageable);
        } else if (brandName != null && !brandName.isEmpty()) {
            pageResult = articleRepository.findByBrandNameContainingIgnoreCase(brandName, pageable);
        } else if (categoryName != null && !categoryName.isEmpty()) {
            pageResult = articleRepository.findByCategoriesNameContainingIgnoreCase(categoryName, pageable);
        } else {
            pageResult = articleRepository.findAll(pageable);
        }

        List<Product> products = productEntityMapper.toListProduct(pageResult.getContent());

        return new PageCustom<>(
                products,
                (int) pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.getNumber(),
                pageRequest.isAscending()
        );
    }


}
