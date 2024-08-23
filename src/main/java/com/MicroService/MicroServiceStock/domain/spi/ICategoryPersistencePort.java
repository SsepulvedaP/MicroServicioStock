package com.MicroService.MicroServiceStock.domain.spi;

import com.MicroService.MicroServiceStock.domain.models.Category;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;

import java.util.List;
import java.util.Optional;

public interface ICategoryPersistencePort {
    void createCategory(Category category);
    List<Category> GetAllCategories();
    Category getCategoryByName(String name);
    void updateCategory(Category category);
    void deleteCategory(String name);
    PageCustom<Category> getCategories(PageRequestCustom pageRequest);

}
