package com.MicroService.MicroServiceStock.domain.exceptions;

public class ProductNotFoundForBrandAndCategoryException extends RuntimeException {
    public ProductNotFoundForBrandAndCategoryException(String message) {
        super(message);
    }
}
