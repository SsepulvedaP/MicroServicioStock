package com.MicroService.MicroServiceStock.domain.usecase;

import com.MicroService.MicroServiceStock.domain.api.IArticleServicePort;
import com.MicroService.MicroServiceStock.domain.exceptions.*;
import com.MicroService.MicroServiceStock.domain.models.Article;
import com.MicroService.MicroServiceStock.domain.models.Category;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;
import com.MicroService.MicroServiceStock.domain.spi.IArticlePersistencePort;
import com.MicroService.MicroServiceStock.infrastructure.exception.ArticleNotFoundException;

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

    @Override
    public PageCustom<Article> getArticlesByPage(PageRequestCustom pageRequest, String brandName, String categoryName) {
        PageCustom<Article> articlesPage = iArticlePersistencePort.getArticlesByPage(pageRequest, brandName, categoryName);

        // Verificar si la página de artículos está vacía
        if (articlesPage.getContent().isEmpty()) {
            if (brandName != null && !brandName.isEmpty() && categoryName != null && !categoryName.isEmpty()) {
                throw new ArticleNotFoundForBrandAndCategoryException(
                        "No hay artículos encontrados con la marca: " + brandName + " y la categoría: " + categoryName);
            } else if (brandName != null && !brandName.isEmpty()) {
                throw new ArticleNotFoundForBrandException(
                        "No hay artículos encontrados con la marca: " + brandName);
            } else if (categoryName != null && !categoryName.isEmpty()) {
                throw new ArticleNotFoundForCategoryException(
                        "No hay artículos encontrados con la categoría: " + categoryName);
            } else {
                throw new ArticleNotFoundException("No se encontraron artículos.");
            }
        }

        // Filtrar categorías para incluir solo id y nombre
        articlesPage.getContent().forEach(article -> {
            List<Category> categories = article.getCategories().stream()
                    .map(category -> new Category(category.getId(), category.getName(), null)) // Excluir descripción
                    .toList();
            article.setCategories(categories);
        });

        return articlesPage;
    }



}

