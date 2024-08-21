package com.MicroService.MicroServiceStock.application.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    //Name
    private String name;
    private String description;
}
