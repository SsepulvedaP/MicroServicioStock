package com.MicroService.MicroServiceStock.application.dto.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
    @NonNull
    private String name;
    @NonNull
    private String description;
}
