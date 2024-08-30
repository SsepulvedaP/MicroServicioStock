package com.MicroService.MicroServiceStock.domain.exceptions;

public class ArticleNotFoundForBrandAndCategoryException extends RuntimeException {
    public ArticleNotFoundForBrandAndCategoryException(String message) {
        super(message);
    }
}
