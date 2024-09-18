package com.MicroService.MicroServiceStock.infrastructure.jpa.adapter;

import com.MicroService.MicroServiceStock.domain.exceptions.DuplicateArticleNameException;
import com.MicroService.MicroServiceStock.domain.models.Product;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;
import com.MicroService.MicroServiceStock.domain.spi.IProductPersistencePort;
import com.MicroService.MicroServiceStock.infrastructure.exception.NoDataFoundException;
import com.MicroService.MicroServiceStock.infrastructure.exception.ProductNotFoundException;
import com.MicroService.MicroServiceStock.infrastructure.jpa.entity.ProductEntity;
import com.MicroService.MicroServiceStock.infrastructure.jpa.mapper.ProductEntityMapper;
import com.MicroService.MicroServiceStock.infrastructure.jpa.repository.IBrandRepository;
import com.MicroService.MicroServiceStock.infrastructure.jpa.repository.ICategoryRepository;
import com.MicroService.MicroServiceStock.infrastructure.jpa.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static com.MicroService.MicroServiceStock.utils.Constants.PRODUCT_NOT_FOUND;


@RequiredArgsConstructor
public class ProductJpaAdapter implements IProductPersistencePort {

    private final IProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;
    private final IBrandRepository brandRepository;
    private final ICategoryRepository categoryRepository;


    @Override
    public void createProduct(Product product) {
        if(productRepository.findByName(product.getName()).isPresent()){
            throw new DuplicateArticleNameException(product.getName());
        }

       ProductEntity productEntity = productEntityMapper.toEntity(product);
        productRepository.save(productEntity);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id)
                .map(productEntityMapper::toProduct);
    }

    @Override
    public List<Product> getAllProducts() {
        List <ProductEntity> productEntityList = productRepository.findAll();
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
            pageResult = productRepository.findByBrandNameContainingIgnoreCaseAndCategoriesNameContainingIgnoreCase(brandName, categoryName, pageable);
        } else if (brandName != null && !brandName.isEmpty()) {
            pageResult = productRepository.findByBrandNameContainingIgnoreCase(brandName, pageable);
        } else if (categoryName != null && !categoryName.isEmpty()) {
            pageResult = productRepository.findByCategoriesNameContainingIgnoreCase(categoryName, pageable);
        } else {
            pageResult = productRepository.findAll(pageable);
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

    @Override
    public void updateQuantity(Long productId, int quantity) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));

        productEntity.setQuantity(quantity);
        productRepository.save(productEntity);
    }


}
