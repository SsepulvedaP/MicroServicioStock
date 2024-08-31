package com.MicroService.MicroServiceStock.domain.exceptions;

public class ProductNotFoundForCategoryException extends RuntimeException {
    public ProductNotFoundForCategoryException(String message) {
        super(message);
    }
}
