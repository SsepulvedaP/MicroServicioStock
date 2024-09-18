package com.MicroService.MicroServiceStock.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductRequest {
    @NotNull
    private Long productId;
    @Positive
    @NotNull
    private int quantity;
}
