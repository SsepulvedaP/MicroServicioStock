package com.MicroService.MicroServiceStock.application.handler;

import com.MicroService.MicroServiceStock.application.dto.request.ProductRequest;
import com.MicroService.MicroServiceStock.application.dto.response.ProductResponse;
import com.MicroService.MicroServiceStock.application.handler.interfaces.IProductHandler;
import com.MicroService.MicroServiceStock.application.mapper.request.ProductRequestMapper;
import com.MicroService.MicroServiceStock.application.mapper.request.ProductRequestMapperImpl;
import com.MicroService.MicroServiceStock.application.mapper.response.ProductResponseMapper;
import com.MicroService.MicroServiceStock.domain.api.IProductServicePort;
import com.MicroService.MicroServiceStock.domain.models.Product;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;



@Service
@Transactional
@RequiredArgsConstructor
public class ProductHandler implements IProductHandler {

    private final IProductServicePort articleServicePort;
    private final ProductRequestMapper productRequestMapper;
    private final ProductResponseMapper productResponseMapper;
    private final ProductRequestMapperImpl articleRequestMapperImpl;

    @Override
    public void createProduct(ProductRequest productRequest) {
        Product product = productRequestMapper.toProduct(productRequest);
        articleServicePort.createProduct(product);
    }

    @Override
    public Optional<ProductResponse> getProductById(Long id) {
        return articleServicePort.getProductById(id)
                .map(productResponseMapper::toResponse);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return articleServicePort.getAllProducts().stream()
                .map(productResponseMapper::toResponse)
                .toList();
    }


    @Override
    public PageCustom<ProductResponse> getProducts(PageRequestCustom pageRequest, String brandName, String categoryName) {

        PageCustom<Product> articlesPage = articleServicePort.getProductsByPage(pageRequest, brandName, categoryName);

        // Transformar a ProductResponse
        List<ProductResponse> productRespons = productResponseMapper.toResponseList(articlesPage.getContent());
        return new PageCustom<>(
                productRespons,
                articlesPage.getTotalElements(),
                articlesPage.getTotalPages(),
                articlesPage.getCurrentPage(),
                articlesPage.isAscending()
        );
    }

}
