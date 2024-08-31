package com.MicroService.MicroServiceStock.domain.api;

import com.MicroService.MicroServiceStock.domain.models.Product;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;

import java.util.List;
import java.util.Optional;

public interface IProductServicePort {
    void createProduct(Product product);
    Optional<Product> getProductById(Long id);
    List<Product> getAllProducts();
    PageCustom<Product> getProductsByPage(PageRequestCustom pageRequest, String brandName, String categoryName);
}
