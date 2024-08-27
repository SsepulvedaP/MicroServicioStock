package com.MicroService.MicroServiceStock.domain.api;

import com.MicroService.MicroServiceStock.domain.models.Brand;

import java.util.List;

public interface IBrandServicePort {
    void createBrand(Brand brand);
    List<Brand> GetAllBrands();
    Brand getBrandByName(String name);
    void updateBrand(Brand brand);
    void deleteBrand(String name);
}
