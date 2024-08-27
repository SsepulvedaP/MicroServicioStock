package com.MicroService.MicroServiceStock.domain.exceptions;

public class InvalidBrandDataException extends RuntimeException{
    public InvalidBrandDataException(String message) {
        super(message);
    }
}
