package com.MicroService.MicroServiceStock.domain.exceptions;

public class ArticleNotFoundForBrandException extends RuntimeException {
    public ArticleNotFoundForBrandException(String message) {
        super(message);
    }
}
