package com.MicroService.MicroServiceStock.application.dto.response;

import com.MicroService.MicroServiceStock.domain.models.Brand;
import com.MicroService.MicroServiceStock.domain.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleResponse {
    private String name;
    private String description;
    private int cantity;
    private double price;
    private Brand brand;
    private List<CategoryArticleResponse> categories;
}
