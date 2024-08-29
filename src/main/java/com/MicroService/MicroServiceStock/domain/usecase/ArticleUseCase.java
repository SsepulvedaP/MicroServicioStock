package com.MicroService.MicroServiceStock.domain.usecase;

import com.MicroService.MicroServiceStock.domain.api.IArticleServicePort;
import com.MicroService.MicroServiceStock.domain.exceptions.DuplicateCategoryNameException;
import com.MicroService.MicroServiceStock.domain.exceptions.InvalidCategoryDataException;
import com.MicroService.MicroServiceStock.domain.models.Article;
import com.MicroService.MicroServiceStock.domain.models.Category;
import com.MicroService.MicroServiceStock.domain.spi.IArticlePersistencePort;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class ArticleUseCase implements IArticleServicePort {

    private final IArticlePersistencePort iArticlePersistencePort;

    public ArticleUseCase(IArticlePersistencePort iArticlePersistencePort) {
        this.iArticlePersistencePort = iArticlePersistencePort;
    }

    @Override
    public void createArticle(Article article) {
        if (article.getCategories() == null || article.getCategories().isEmpty()) {
            throw new InvalidCategoryDataException("El artículo debe tener al menos una categoría.");
        }

        if (article.getCategories().size() > 3) {
            throw new InvalidCategoryDataException("El artículo no puede tener más de 3 categorías.");
        }

        // Convertir la lista a un Set para verificar duplicados
        Set<Category> uniqueCategories = new HashSet<>(article.getCategories());
        if (uniqueCategories.size() < article.getCategories().size()) {
            throw new DuplicateCategoryNameException("El artículo no puede tener categorías duplicadas.");
        }

        // Si pasa todas las validaciones, se crea el artículo
        iArticlePersistencePort.createArticle(article);
    }

    @Override
    public Optional<Article> getArticleById(Long id) {
        return iArticlePersistencePort.getArticleById(id);
    }

    @Override
    public List<Article> getAllArticles() {
        return iArticlePersistencePort.getAllArticles();
    }
}
