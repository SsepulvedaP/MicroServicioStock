package com.MicroService.MicroServiceStock.application.mapper.request;


import com.MicroService.MicroServiceStock.application.dto.request.CategoryRequest;
import com.MicroService.MicroServiceStock.domain.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CategoryRequestMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Category toCategory(CategoryRequest categoryRequest);
}
