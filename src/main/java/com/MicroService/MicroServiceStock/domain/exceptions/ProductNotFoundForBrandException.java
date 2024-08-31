package com.MicroService.MicroServiceStock.domain.exceptions;

public class ProductNotFoundForBrandException extends RuntimeException {
    public ProductNotFoundForBrandException(String message) {
        super(message);
    }
}
