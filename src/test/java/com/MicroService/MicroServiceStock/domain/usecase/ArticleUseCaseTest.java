package com.MicroService.MicroServiceStock.domain.usecase;

import com.MicroService.MicroServiceStock.domain.exceptions.DuplicateCategoryNameException;
import com.MicroService.MicroServiceStock.domain.exceptions.InvalidCategoryDataException;
import com.MicroService.MicroServiceStock.domain.models.Article;
import com.MicroService.MicroServiceStock.domain.models.Category;
import com.MicroService.MicroServiceStock.domain.spi.IArticlePersistencePort;
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
}