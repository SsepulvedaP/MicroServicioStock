package com.MicroService.MicroServiceStock.application.mapper.response;


import com.MicroService.MicroServiceStock.application.dto.response.ProductResponse;
import com.MicroService.MicroServiceStock.domain.models.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductResponseMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    ProductResponse toResponse(Product product);

    default List<ProductResponse> toResponseList(List<Product> productList) {
        return productList.stream()
                .map(this::toResponse)
                .toList();
    }
}
