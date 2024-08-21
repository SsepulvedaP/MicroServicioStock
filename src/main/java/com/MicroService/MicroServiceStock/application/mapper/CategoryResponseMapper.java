package com.MicroService.MicroServiceStock.application.mapper;


import com.MicroService.MicroServiceStock.application.dto.CategoryResponse;
import com.MicroService.MicroServiceStock.domain.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {

    CategoryResponse toResponse(Category category);
    List<CategoryResponse> toResponseList(List<Category> categories);
}
