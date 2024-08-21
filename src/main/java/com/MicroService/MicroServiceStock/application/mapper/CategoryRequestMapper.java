package com.MicroService.MicroServiceStock.application.mapper;


import com.MicroService.MicroServiceStock.application.dto.CategoryRequest;
import com.MicroService.MicroServiceStock.domain.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface CategoryRequestMapper {
    Category toCategory(CategoryRequest categoryRequest);
}
