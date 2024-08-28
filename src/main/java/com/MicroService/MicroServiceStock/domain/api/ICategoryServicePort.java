package com.MicroService.MicroServiceStock.domain.api;

import com.MicroService.MicroServiceStock.domain.models.Category;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;

import java.util.List;

public interface ICategoryServicePort {
    void createCategory(Category category);
    List<Category> getAllCategories();
    Category getCategoryByName(String name);
    void updateCategory(Category category);
    void deleteCategory(String name);
    PageCustom<Category> getCategories(PageRequestCustom pageRequest);
}
