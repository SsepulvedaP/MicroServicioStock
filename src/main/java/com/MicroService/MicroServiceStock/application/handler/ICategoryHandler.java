package com.MicroService.MicroServiceStock.application.handler;

import com.MicroService.MicroServiceStock.application.dto.CategoryRequest;
import com.MicroService.MicroServiceStock.application.dto.CategoryResponse;
import com.MicroService.MicroServiceStock.domain.models.Category;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;


import java.util.List;

public interface ICategoryHandler {
    void createCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> GetAllCategories();
    CategoryResponse getCategoryByName(String name);
    void updateCategory(CategoryRequest categoryRequest);
    void deleteCategory(String name);
    PageCustom<CategoryResponse> getCategories(PageRequestCustom pageRequest);
}
