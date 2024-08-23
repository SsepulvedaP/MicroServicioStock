package com.MicroService.MicroServiceStock.application.mapper;


import com.MicroService.MicroServiceStock.application.dto.CategoryRequest;
import com.MicroService.MicroServiceStock.domain.models.Category;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CategoryRequestMapper {
    Category toCategory(CategoryRequest categoryRequest);
}
