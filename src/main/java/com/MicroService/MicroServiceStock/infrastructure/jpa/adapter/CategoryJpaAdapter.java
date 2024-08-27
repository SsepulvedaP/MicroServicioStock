package com.MicroService.MicroServiceStock.infrastructure.jpa.adapter;


import com.MicroService.MicroServiceStock.domain.exceptions.DuplicateCategoryNameException;
import com.MicroService.MicroServiceStock.domain.models.Category;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;
import com.MicroService.MicroServiceStock.domain.spi.ICategoryPersistencePort;
import com.MicroService.MicroServiceStock.infrastructure.exception.CategoryNotFoundException;
import com.MicroService.MicroServiceStock.infrastructure.exception.NoDataFoundException;
import com.MicroService.MicroServiceStock.infrastructure.jpa.entity.CategoryEntity;
import com.MicroService.MicroServiceStock.infrastructure.jpa.mapper.CategoryEntityMapper;
import com.MicroService.MicroServiceStock.infrastructure.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;


@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;


    @Override
    public void createCategory(Category category) {
        if(categoryRepository.findByName(category.getName()).isPresent()){
            throw new DuplicateCategoryNameException(category.getName());
        }
        CategoryEntity categoryEntity = categoryEntityMapper.toEntity(category);
        categoryRepository.save(categoryEntity);
    }

    @Override
    public List<Category> GetAllCategories() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        if(categoryEntityList.isEmpty()){
            throw new NoDataFoundException();
        }

        return categoryEntityMapper.toListCategory(categoryEntityList);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryEntityMapper.toCategory
                (categoryRepository.findByName(name)
                        .orElseThrow(CategoryNotFoundException::new));
    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.toEntity(category));

    }

    @Override
    public void deleteCategory(String name) {
        categoryRepository.deleteByName(name);

    }

    @Override
    public PageCustom<Category> getCategories(PageRequestCustom pageRequest) {
        PageRequest pageRequestSpring = PageRequest.of(
                pageRequest.getPage(),
                pageRequest.getSize(),
                pageRequest.isAscending() ? Sort.by(pageRequest.getSortField()).ascending() : Sort.by(pageRequest.getSortField()).descending()
        );

        Page<CategoryEntity> categoryEntityPage = categoryRepository.findAll(pageRequestSpring);

        List<Category> categories = categoryEntityMapper.toListCategory(categoryEntityPage.getContent());

        return new PageCustom<>(
                categories,
                (int) categoryEntityPage.getTotalElements(),
                categoryEntityPage.getTotalPages(),
                categoryEntityPage.getNumber(),
                pageRequest.isAscending()
        );
    }


}
