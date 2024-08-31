package com.MicroService.MicroServiceStock.utils;
import com.MicroService.MicroServiceStock.domain.exceptions.ProductNotFoundForBrandAndCategoryException;
import com.MicroService.MicroServiceStock.domain.exceptions.ProductNotFoundForBrandException;
import com.MicroService.MicroServiceStock.domain.exceptions.ProductNotFoundForCategoryException;
import com.MicroService.MicroServiceStock.infrastructure.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleArticleNotFoundException(ProductNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundForBrandException.class)
    public ResponseEntity<ErrorResponse> handleArticleNotFoundForBrandException(ProductNotFoundForBrandException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundForCategoryException.class)
    public ResponseEntity<ErrorResponse> handleArticleNotFoundForCategoryException(ProductNotFoundForCategoryException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundForBrandAndCategoryException.class)
    public ResponseEntity<ErrorResponse> handleArticleNotFoundForBrandAndCategoryException(ProductNotFoundForBrandAndCategoryException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()) {
        };
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
