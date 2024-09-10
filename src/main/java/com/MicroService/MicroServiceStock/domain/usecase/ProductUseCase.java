package com.MicroService.MicroServiceStock.domain.usecase;

import com.MicroService.MicroServiceStock.domain.api.IProductServicePort;
import com.MicroService.MicroServiceStock.domain.exceptions.*;
import com.MicroService.MicroServiceStock.domain.models.Product;
import com.MicroService.MicroServiceStock.domain.models.Category;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;
import com.MicroService.MicroServiceStock.domain.spi.IProductPersistencePort;
import com.MicroService.MicroServiceStock.infrastructure.exception.ProductNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.MicroService.MicroServiceStock.utils.Constants.*;


public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort iProductPersistencePort;

    public ProductUseCase(IProductPersistencePort iProductPersistencePort) {
        this.iProductPersistencePort = iProductPersistencePort;
    }

    @Override
    public void createProduct(Product product) {



        if (product.getCategories() == null || product.getCategories().isEmpty()) {
            throw new InvalidCategoryDataException(CATEGORY_DATA_FOR_PRODUCT);
        }

        if (product.getCategories().size() > 3) {
            throw new InvalidCategoryDataException(EXCEEDED_CATEGORY_LIMIT_FOR_PRODUCT);
        }

        // Convertir la lista a un Set para verificar duplicados
        Set<Category> uniqueCategories = new HashSet<>(product.getCategories());
        if (uniqueCategories.size() < product.getCategories().size()) {
            throw new DuplicateCategoryNameException(DUPLICATE_CATEGORY_FOR_PRODUCT);
        }

        // Si pasa todas las validaciones, se crea el artículo
        iProductPersistencePort.createProduct(product);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return iProductPersistencePort.getProductById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return iProductPersistencePort.getAllProducts();
    }

    @Override
    public PageCustom<Product> getProductsByPage(PageRequestCustom pageRequest, String brandName, String categoryName) {
        PageCustom<Product> articlesPage = iProductPersistencePort.getProductsByPage(pageRequest, brandName, categoryName);

        // Verificar si la página de artículos está vacía
        if (articlesPage.getContent().isEmpty()) {
            if (brandName != null && !brandName.isEmpty() && categoryName != null && !categoryName.isEmpty()) {
                throw new ProductNotFoundForBrandAndCategoryException(PRODUCT_NOT_FOUND_FOR_BRAND_AND_CATEGORY);
            } else if (brandName != null && !brandName.isEmpty()) {
                throw new ProductNotFoundForBrandException(PRODUCT_NOT_FOUND_FOR_BRAND);
            } else if (categoryName != null && !categoryName.isEmpty()) {
                throw new ProductNotFoundForCategoryException(PRODUCT_NOT_FOUND_FOR_CATEGORY);
            } else {
                throw new ProductNotFoundException(PRODUCT_NOT_FOUND);
            }
        }

        // Filtrar categorías para incluir solo id y nombre
        articlesPage.getContent().forEach(article -> {
            List<Category> categories = article.getCategories().stream()
                    .map(category -> new Category(category.getId(), category.getName(), null))
                    .toList();
            article.setCategories(categories);
        });

        return articlesPage;
    }



}

