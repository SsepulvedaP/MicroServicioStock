package com.MicroService.MicroServiceStock.domain.usecase;

import com.MicroService.MicroServiceStock.domain.exceptions.InvalidCategoryDataException;
import com.MicroService.MicroServiceStock.domain.models.Category;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;
import com.MicroService.MicroServiceStock.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCategory_ShouldThrowException_WhenNameExceedsMaxLength() {
        Category category = new Category();
        category.setName("NombreDemasiadoLargoQueExcedeCincuentaCaracteresDeLargo123456");
        category.setDescription("Descripción válida");

        assertThrows(InvalidCategoryDataException.class, () -> categoryUseCase.createCategory(category));
        verify(categoryPersistencePort, never()).createCategory(any(Category.class));
    }

    @Test
    void createCategory_ShouldThrowException_WhenDescriptionExceedsMaxLength() {
        Category category = new Category();
        category.setName("Nombre válido");
        category.setDescription("UnaDescripciónMuyMuyMuyLargaQueSeguroExcedeLosNoventaCaracteresMuyExageradamenteParaForzarLaExcepcionnn");

        try {
            categoryUseCase.createCategory(category);
            fail("Se esperaba que se lanzara InvalidCategoryDataException, pero no se lanzó.");
        } catch (InvalidCategoryDataException e) {
            assertEquals("La descripción de la categoría no puede exceder los 90 caracteres.", e.getMessage());
        }
    }

    @Test
    void createCategory_ShouldCallPersistencePort_WhenDataIsValid() {
        Category category = new Category();
        category.setName("Nombre válido");
        category.setDescription("Descripción válida");

        categoryUseCase.createCategory(category);

        verify(categoryPersistencePort).createCategory(category);
    }

    @Test
    void getAllCategories_ShouldReturnCategories_WhenCategoriesExist() {
        List<Category> mockCategories = Arrays.asList(
                new Category(1L, "Categoría 1", "Descripción 1"),
                new Category(2L, "Categoría 2", "Descripción 2")
        );
        when(categoryPersistencePort.GetAllCategories()).thenReturn(mockCategories);

        List<Category> categories = categoryUseCase.getAllCategories();

        assertEquals(2, categories.size());
        verify(categoryPersistencePort).GetAllCategories();
    }

    @Test
    void getAllCategories_ShouldReturnEmptyList_WhenNoCategoriesExist() {
        when(categoryPersistencePort.GetAllCategories()).thenReturn(Arrays.asList());

        List<Category> categories = categoryUseCase.getAllCategories();

        assertTrue(categories.isEmpty());
        verify(categoryPersistencePort).GetAllCategories();
    }

    @Test
    void getCategories_ShouldReturnCategoriesPage_WhenCategoriesExist() {
        // Datos de prueba
        PageRequestCustom pageRequest = new PageRequestCustom(0, 10, true, "name");
        PageCustom<Category> expectedPage = new PageCustom<>();

        // Agrega categorías a expectedPage si es necesario
        List<Category> mockCategories = Arrays.asList(
                new Category(1L, "Carros 1", "Descripción 1"),
                new Category(2L, "Sombreros 2", "Descripción 2")
        );
        expectedPage.setContent(mockCategories);
        expectedPage.setTotalElements(2); // Asumiendo que hay 2 categorías en total
        expectedPage.setTotalPages(1); // Asumiendo que todas caben en una página

        // Configuración del comportamiento esperado
        when(categoryPersistencePort.getCategories(pageRequest)).thenReturn(expectedPage);

        // Ejecución del metodo
        PageCustom<Category> result = categoryUseCase.getCategories(pageRequest);

        // Verificación
        assertEquals(expectedPage, result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(mockCategories, result.getContent());
        verify(categoryPersistencePort).getCategories(pageRequest);
    }

}