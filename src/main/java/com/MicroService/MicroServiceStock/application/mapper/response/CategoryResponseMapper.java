package com.MicroService.MicroServiceStock.application.mapper.response;


import com.MicroService.MicroServiceStock.application.dto.response.CategoryResponse;
import com.MicroService.MicroServiceStock.domain.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    CategoryResponse toResponse(Category category);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    List<CategoryResponse> toResponseList(List<Category> categories);
}
