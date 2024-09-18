package com.MicroService.MicroServiceStock.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import static com.MicroService.MicroServiceStock.utils.Constants.MIN_CATEGORY_FOR_PRODUCT;
import static com.MicroService.MicroServiceStock.utils.Constants.PRICE_GREATER_THAN_ZERO;

@Getter
@Setter
public class ProductRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Min(1)
    private int quantity;
    @NotBlank
    @Positive(message = PRICE_GREATER_THAN_ZERO)
    private double price;
    @NotNull
    private Long brandId;
    @NotNull
    @Size(min = 1, max = 3, message = MIN_CATEGORY_FOR_PRODUCT)
    private Set<Long> categoryIds;
}
