package com.MicroService.MicroServiceStock.application.dto.request;


import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    @NonNull
    private String name;
    @NotBlank
    @Size(min = 2, max = 90)
    @NonNull
    private String description;
}
