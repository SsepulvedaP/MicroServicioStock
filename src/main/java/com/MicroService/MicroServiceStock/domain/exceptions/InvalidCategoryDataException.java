package com.MicroService.MicroServiceStock.domain.exceptions;

public class InvalidCategoryDataException extends RuntimeException{
    public InvalidCategoryDataException(String message) {
        super(message);
    }
}
