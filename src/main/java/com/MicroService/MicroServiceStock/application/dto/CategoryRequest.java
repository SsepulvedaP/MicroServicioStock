package com.MicroService.MicroServiceStock.application.dto;


import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    @NonNull
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;
    @NonNull
    @NotBlank
    @Size(min = 2, max = 90)
    private String description;
}
