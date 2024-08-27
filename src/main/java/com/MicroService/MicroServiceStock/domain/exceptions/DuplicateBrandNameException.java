package com.MicroService.MicroServiceStock.domain.exceptions;

public class DuplicateBrandNameException extends RuntimeException{
    public DuplicateBrandNameException(String message) {
        super(message);
    }
}
