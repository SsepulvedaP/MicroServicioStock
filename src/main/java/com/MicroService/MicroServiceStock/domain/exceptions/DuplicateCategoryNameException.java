package com.MicroService.MicroServiceStock.domain.exceptions;

public class DuplicateCategoryNameException extends RuntimeException{
    public DuplicateCategoryNameException(String message) {
        super(message);
    }
}
