package com.MicroService.MicroServiceStock.infrastructure.exception;

public class ArticleNotFoundException extends RuntimeException {
  public ArticleNotFoundException(String message) {
    super(message);
  }
}
