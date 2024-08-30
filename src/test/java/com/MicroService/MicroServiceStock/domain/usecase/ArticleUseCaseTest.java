package com.MicroService.MicroServiceStock.domain.usecase;

import com.MicroService.MicroServiceStock.domain.exceptions.*;
import com.MicroService.MicroServiceStock.domain.models.Article;
import com.MicroService.MicroServiceStock.domain.models.Category;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;
import com.MicroService.MicroServiceStock.domain.spi.IArticlePersistencePort;
import com.MicroService.MicroServiceStock.infrastructure.exception.ArticleNotFoundException;
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

class ArticleUseCaseTest {

    @Mock
    private IArticlePersistencePort articlePersistencePort;

    private ArticleUseCase articleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        articleUseCase = new ArticleUseCase(articlePersistencePort);
    }

    @Test
    void createArticle_WithValidData_ShouldCreateArticle() {
        Article article = new Article();
        article.setCategories(Arrays.asList(new Category(), new Category()));

        articleUseCase.createArticle(article);

        verify(articlePersistencePort, times(1)).createArticle(article);
    }

    @Test
    void createArticle_WithNoCategories_ShouldThrowException() {
        Article article = new Article();
        article.setCategories(Collections.emptyList());

        assertThrows(InvalidCategoryDataException.class, () -> articleUseCase.createArticle(article));
    }

    @Test
    void createArticle_WithMoreThanThreeCategories_ShouldThrowException() {
        Article article = new Article();
        article.setCategories(Arrays.asList(new Category(), new Category(), new Category(), new Category()));

        assertThrows(InvalidCategoryDataException.class, () -> articleUseCase.createArticle(article));
    }

    @Test
    void createArticle_WithDuplicateCategories_ShouldThrowException() {
        Category category = new Category();
        category.setName("Duplicate");
        Article article = new Article();
        article.setCategories(Arrays.asList(category, category));

        assertThrows(DuplicateCategoryNameException.class, () -> articleUseCase.createArticle(article));
    }

    @Test
    void getArticleById_WithExistingId_ShouldReturnArticle() {
        Long id = 1L;
        Article expectedArticle = new Article();
        when(articlePersistencePort.getArticleById(id)).thenReturn(Optional.of(expectedArticle));

        Optional<Article> result = articleUseCase.getArticleById(id);

        assertTrue(result.isPresent());
        assertEquals(expectedArticle, result.get());
    }

    @Test
    void getArticleById_WithNonExistingId_ShouldReturnEmpty() {
        Long id = 1L;
        when(articlePersistencePort.getArticleById(id)).thenReturn(Optional.empty());

        Optional<Article> result = articleUseCase.getArticleById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void getAllArticles_ShouldReturnListOfArticles() {
        List<Article> expectedArticles = Arrays.asList(new Article(), new Article());
        when(articlePersistencePort.getAllArticles()).thenReturn(expectedArticles);

        List<Article> result = articleUseCase.getAllArticles();

        assertEquals(expectedArticles, result);
    }


    @Test
    void getArticlesByPage_WhenPageIsEmptyAndBrandAndCategoryAreProvided_ShouldThrowArticleNotFoundForBrandAndCategoryException() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        when(articlePersistencePort.getArticlesByPage(pageRequest, "Nike", "Shoes"))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));

        assertThrows(ArticleNotFoundForBrandAndCategoryException.class, () ->
                articleUseCase.getArticlesByPage(pageRequest, "Nike", "Shoes"));
    }

    @Test
    void getArticlesByPage_WhenPageIsEmptyAndBrandIsProvided_ShouldThrowArticleNotFoundForBrandException() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        when(articlePersistencePort.getArticlesByPage(pageRequest, "Nike", null))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));

        assertThrows(ArticleNotFoundForBrandException.class, () ->
                articleUseCase.getArticlesByPage(pageRequest, "Nike", null));
    }

    @Test
    void getArticlesByPage_WhenPageIsEmptyAndCategoryIsProvided_ShouldThrowArticleNotFoundForCategoryException() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        when(articlePersistencePort.getArticlesByPage(pageRequest, null, "Shoes"))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));

        assertThrows(ArticleNotFoundForCategoryException.class, () ->
                articleUseCase.getArticlesByPage(pageRequest, null, "Shoes"));
    }

    @Test
    void getArticlesByPage_WhenPageIsEmptyAndNoBrandOrCategoryProvided_ShouldThrowArticleNotFoundException() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        when(articlePersistencePort.getArticlesByPage(pageRequest, null, null))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));
        when(articlePersistencePort.getArticlesByPage(pageRequest, null, null))
                .thenReturn(new PageCustom<>(Collections.emptyList(), 1, 10, 0, true));

        assertThrows(ArticleNotFoundException.class, () ->
                articleUseCase.getArticlesByPage(pageRequest, null, null));
    }

    @Test
    void getArticlesByPage_WhenArticlesArePresent_ShouldFilterCategoriesAndReturnPage() {
        PageRequestCustom pageRequest = new PageRequestCustom(1, 10, true, "name");
        Article article = new Article();
        Category category = new Category(1L, "Electronics", "Devices and gadgets");
        article.setCategories(List.of(category));

        PageCustom<Article> expectedPage = new PageCustom<>(List.of(article), 1, 10, 1, true);
        when(articlePersistencePort.getArticlesByPage(pageRequest, null, null))
                .thenReturn(expectedPage);

        PageCustom<Article> result = articleUseCase.getArticlesByPage(pageRequest, null, null);

        assertEquals(1, result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getCategories().get(0).getId());
        assertEquals("Electronics", result.getContent().get(0).getCategories().get(0).getName());
        assertEquals(null, result.getContent().get(0).getCategories().get(0).getDescription());

        verify(articlePersistencePort, times(1)).getArticlesByPage(pageRequest, null, null);
    }

}