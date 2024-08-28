package com.MicroService.MicroServiceStock.infrastructure.jpa.mapper;


import com.MicroService.MicroServiceStock.domain.models.Category;
import com.MicroService.MicroServiceStock.infrastructure.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {
    CategoryEntity toEntity(Category category);
    Category toCategory(CategoryEntity categoryEntity);
    List<Category> toListCategory(List<CategoryEntity> categoryEntityList);
}
