package com.MicroService.MicroServiceStock.domain.usecase;

import com.MicroService.MicroServiceStock.domain.api.ICategoryServicePort;
import com.MicroService.MicroServiceStock.domain.exceptions.DuplicateCategoryNameException;
import com.MicroService.MicroServiceStock.domain.exceptions.InvalidCategoryDataException;
import com.MicroService.MicroServiceStock.domain.models.Category;
import com.MicroService.MicroServiceStock.domain.spi.ICategoryPersistencePort;


import java.util.List;
import java.util.Optional;


public class CategoryUseCase implements ICategoryServicePort {

  private final ICategoryPersistencePort categoryPersistencePort;

  public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
      this.categoryPersistencePort = categoryPersistencePort;
  }

    @Override
    public void createCategory(Category category) {

        // Validación: El nombre no debe exceder 50 caracteres
        if (category.getName().length() > 50 ) {
            throw new InvalidCategoryDataException("El nombre de la categoría no puede exceder los 50 caracteres.");
        }

        // Validación: La descripción no debe exceder 90 caracteres
        if (category.getDescription().length() > 90) {
            throw new InvalidCategoryDataException("La descripción de la categoría no puede exceder los 90 caracteres.");
        }
        // Guardar la categoría si todas las validaciones pasan
        categoryPersistencePort.createCategory(category);

    }

    @Override
    public List<Category> GetAllCategories() {
        return categoryPersistencePort.GetAllCategories();
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryPersistencePort.getCategoryByName(name);
    }


    @Override
    public void updateCategory(Category category) {

    }

    @Override
    public void deleteCategory(Category category) {

    }
}
