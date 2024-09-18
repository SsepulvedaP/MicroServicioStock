package com.MicroService.MicroServiceStock.domain.usecase;

import com.MicroService.MicroServiceStock.domain.exceptions.*;
import com.MicroService.MicroServiceStock.domain.models.Product;
import com.MicroService.MicroServiceStock.domain.models.Category;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;
import com.MicroService.MicroServiceStock.domain.spi.IProductPersistencePort;
import com.MicroService.MicroServiceStock.infrastructure.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductUseCaseTest {

    @Mock
    private IProductPersistencePort articlePersistencePort;

    private ProductUseCase articleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        articleUseCase = new ProductUseCase(articlePersistencePort);
    }

    @Test
    void createArticle_WithValidData_ShouldCreateProduct() {
        Product product = new Product();
        product.setCategories(Arrays.asList(new Category(), new Category()));

        articleUseCase.createProduct(product);

        verify(articlePersistencePort, times(1)).createProduct(product);
    }

    @Test
    void createProduct_WithNoCategories_ShouldThrowException() {
        Product product = new Product();
        product.setCategories(Collections.emptyList());

        assertThrows(InvalidCategoryDataException.class, () -> articleUseCase.createProduct(product));
    }

    @Test
    void createProduct_WithMoreThanThreeCategories_ShouldThrowException() {
        Product product = new Product();
        product.setCategories(Arrays.asList(new Category(), new Category(), new Category(), new Category()));

        assertThrows(InvalidCategoryDataException.class, () -> articleUseCase.createProduct(product));
    }

    @Test
    void createProduct_WithDuplicateCategories_ShouldThrowException() {
        Category category = new Category();
        category.setName("Duplicate");
        Product product = new Product();
        product.setCategories(Arrays.asList(category, category));

        assertThrows(DuplicateCategoryNameException.class, () -> articleUseCase.createProduct(product));
    }

    @Test
    void getArticleById_WithExistingId_ShouldReturnProduct() {
        Long id = 1L;
        Product expectedProduct = new Product();
        when(articlePersistencePort.getProductById(id)).thenReturn(Optional.of(expectedProduct));

        Optional<Product> result = articleUseCase.getProductById(id);

        assertTrue(result.isPresent());
        assertEquals(expectedProduct, result.get());
    }

    @Test
    void getProductById_WithNonExistingId_ShouldReturnEmpty() {
        Long id = 1L;
        when(articlePersistencePort.getProductById(id)).thenReturn(Optional.empty());

        Optional<Product> result = articleUseCase.getProductById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void getAllArticles_ShouldReturnListOfProducts() {
        List<Product> expectedProducts = Arrays.asList(new Product(), new Product());
        when(articlePersistencePort.getAllProducts()).thenReturn(expectedProducts);

        List<Product> result = articleUseCase.getAllProducts();

        assertEquals(expectedProducts, result);
    }


    @Test
    void getProductsByPage_WhenPageIsEmptyAndBrandAndCategoryAreProvided_ShouldThrowArticleNotFoundForBrandAndCategoryException() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        when(articlePersistencePort.getProductsByPage(pageRequest, "Nike", "Shoes"))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));

        assertThrows(ProductNotFoundForBrandAndCategoryException.class, () ->
                articleUseCase.getProductsByPage(pageRequest, "Nike", "Shoes"));
    }

    @Test
    void getProductsByPage_WhenPageIsEmptyAndBrandIsProvided_ShouldThrowArticleNotFoundForBrandException() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        when(articlePersistencePort.getProductsByPage(pageRequest, "Nike", null))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));

        assertThrows(ProductNotFoundForBrandException.class, () ->
                articleUseCase.getProductsByPage(pageRequest, "Nike", null));
    }

    @Test
    void getProductsByPage_WhenPageIsEmptyAndCategoryIsProvided_ShouldThrowArticleNotFoundForCategoryException() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        when(articlePersistencePort.getProductsByPage(pageRequest, null, "Shoes"))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));

        assertThrows(ProductNotFoundForCategoryException.class, () ->
                articleUseCase.getProductsByPage(pageRequest, null, "Shoes"));
    }

    @Test
    void getProductsByPage_WhenPageIsEmptyAndNoBrandOrCategoryProvided_ShouldThrowArticleNotFoundException() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        when(articlePersistencePort.getProductsByPage(pageRequest, null, null))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));
        when(articlePersistencePort.getProductsByPage(pageRequest, null, null))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));

        assertThrows(ProductNotFoundException.class, () ->
                articleUseCase.getProductsByPage(pageRequest, null, null));
    }

    @Test
    void getArticlesByPage_WhenProductsArePresent_ShouldFilterCategoriesAndReturnPage() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        Product product = new Product();
        Category category = new Category(1L, "Electronics", "Devices and gadgets");
        product.setCategories(List.of(category));

        PageCustom<Product> expectedPage = new PageCustom<>(List.of(product), 1, 10, 1, true);
        when(articlePersistencePort.getProductsByPage(pageRequest, null, null))
                .thenReturn(expectedPage);

        PageCustom<Product> result = articleUseCase.getProductsByPage(pageRequest, null, null);

        assertEquals(1, result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getCategories().get(0).getId());
        assertEquals("Electronics", result.getContent().get(0).getCategories().get(0).getName());
        assertEquals(null, result.getContent().get(0).getCategories().get(0).getDescription());

        verify(articlePersistencePort, times(1)).getProductsByPage(pageRequest, null, null);
    }

    @Test
    void updateQuantity_WithExistingId_ShouldUpdateQuantity() {
        Long id = 1L;
        int quantity = 5;
        Product product = new Product();
        product.setId(id);
        product.setQuantity(10);

        when(articlePersistencePort.getProductById(id)).thenReturn(Optional.of(product));

        articleUseCase.updateQuantity(id, quantity);

        verify(articlePersistencePort, times(1)).updateQuantity(id, quantity);
    }

    @Test
    void updateQuantity_WithNonExistingId_ShouldThrowProductNotFoundException() {
        Long id = 1L;
        int quantity = 5;

        when(articlePersistencePort.getProductById(id)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> articleUseCase.updateQuantity(id, quantity));
    }

}