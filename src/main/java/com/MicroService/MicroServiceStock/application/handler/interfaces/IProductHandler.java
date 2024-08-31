package com.MicroService.MicroServiceStock.application.handler.interfaces;

import com.MicroService.MicroServiceStock.application.dto.request.ProductRequest;
import com.MicroService.MicroServiceStock.application.dto.response.ProductResponse;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;


import java.util.List;
import java.util.Optional;

public interface IProductHandler {
    void createProduct(ProductRequest productRequest);
    Optional<ProductResponse> getProductById(Long id);
    List<ProductResponse> getAllProducts();
    PageCustom<ProductResponse> getProducts(PageRequestCustom pageRequest, String brandName, String categoryName);
}
