package com.MicroService.MicroServiceStock.application.dto.response;

import com.MicroService.MicroServiceStock.domain.models.Brand;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductResponse {
    private String name;
    private String description;
    private int quantity;
    private double price;
    private Brand brand;
    private List<CategoryProductResponse> categories;
}
