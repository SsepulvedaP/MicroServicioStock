package com.MicroService.MicroServiceStock.application.handler;

import com.MicroService.MicroServiceStock.application.dto.request.ProductRequest;
import com.MicroService.MicroServiceStock.application.dto.request.UpdateProductRequest;
import com.MicroService.MicroServiceStock.application.dto.response.ProductResponse;
import com.MicroService.MicroServiceStock.application.handler.interfaces.IProductHandler;
import com.MicroService.MicroServiceStock.application.mapper.request.ProductRequestMapper;
import com.MicroService.MicroServiceStock.application.mapper.response.ProductResponseMapper;
import com.MicroService.MicroServiceStock.domain.api.IProductServicePort;
import com.MicroService.MicroServiceStock.domain.models.Product;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;
import com.MicroService.MicroServiceStock.infrastructure.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.MicroService.MicroServiceStock.utils.Constants.PRODUCT_NOT_FOUND;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductHandler implements IProductHandler {

    private final IProductServicePort productServicePort;
    private final ProductRequestMapper productRequestMapper;
    private final ProductResponseMapper productResponseMapper;


    @Override
    public void createProduct(ProductRequest productRequest) {
        Product product = productRequestMapper.toProduct(productRequest);
        productServicePort.createProduct(product);
    }

    @Override
    public Optional<ProductResponse> getProductById(Long id) {
        return productServicePort.getProductById(id)
                .map(productResponseMapper::toResponse);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productServicePort.getAllProducts().stream()
                .map(productResponseMapper::toResponse)
                .toList();
    }


    @Override
    public PageCustom<ProductResponse> getProducts(PageRequestCustom pageRequest, String brandName, String categoryName) {

        PageCustom<Product> articlesPage = productServicePort.getProductsByPage(pageRequest, brandName, categoryName);

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

    @Override
    public void updateProduct(UpdateProductRequest updateProductRequest) {
        Long productId = updateProductRequest.getProductId();
        int newQuantity = updateProductRequest.getQuantity();

        // Obtener el producto por su ID
        Product product = productServicePort.getProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));

        int updatedQuantity = product.getQuantity() + newQuantity;

        // Actualizar la cantidad del producto
        productServicePort.updateQuantity(productId, updatedQuantity);
    }

}
