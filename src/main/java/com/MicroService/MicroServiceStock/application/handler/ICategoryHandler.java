package com.MicroService.MicroServiceStock.application.handler;

import com.MicroService.MicroServiceStock.application.dto.CategoryRequest;
import com.MicroService.MicroServiceStock.application.dto.CategoryResponse;



import java.util.List;

public interface ICategoryHandler {
    void createCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> GetAllCategories();
    CategoryResponse getCategoryByName(String name);
    void updateCategory(CategoryRequest categoryRequest);
    void deleteCategory(CategoryRequest categoryRequest);
}
