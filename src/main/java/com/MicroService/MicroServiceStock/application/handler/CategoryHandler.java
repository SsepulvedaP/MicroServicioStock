package com.MicroService.MicroServiceStock.application.handler;

import com.MicroService.MicroServiceStock.application.dto.CategoryRequest;
import com.MicroService.MicroServiceStock.application.dto.CategoryResponse;

import com.MicroService.MicroServiceStock.application.mapper.CategoryRequestMapper;
import com.MicroService.MicroServiceStock.application.mapper.CategoryResponseMapper;
import com.MicroService.MicroServiceStock.domain.api.ICategoryServicePort;
import com.MicroService.MicroServiceStock.domain.models.Category;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class CategoryHandler implements ICategoryHandler{

    private final ICategoryServicePort categoryServicePort;
    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;


    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.toCategory(categoryRequest);
        categoryServicePort.createCategory(category);

    }

    @Override
    public List<CategoryResponse> GetAllCategories() {
        return categoryResponseMapper.toResponseList(categoryServicePort.GetAllCategories());
    }

    @Override
    public CategoryResponse getCategoryByName(String name) {
        Category category = categoryServicePort.getCategoryByName(name);
        return categoryResponseMapper.toResponse(category);
    }

    @Override
    public void updateCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.toCategory(categoryRequest);
        categoryServicePort.updateCategory(category);
    }

    @Override
    public void deleteCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.toCategory(categoryRequest);
        categoryServicePort.deleteCategory(category);
    }

    @Override
    public PageCustom<CategoryResponse> getCategories(PageRequestCustom pageRequest) {
        PageCustom<Category> categoriesPage = categoryServicePort.getCategories(pageRequest);
        List<CategoryResponse> responseList = categoryResponseMapper.toResponseList(categoriesPage.getContent());
        return new PageCustom<>(
                responseList,
                categoriesPage.getTotalElements(),
                categoriesPage.getTotalPages(),
                categoriesPage.getCurrentPage(),
                categoriesPage.isAscending()
        );
    }


}
