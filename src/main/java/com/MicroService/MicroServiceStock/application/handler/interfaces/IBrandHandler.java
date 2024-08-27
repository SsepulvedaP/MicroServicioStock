package com.MicroService.MicroServiceStock.application.handler.interfaces;

import com.MicroService.MicroServiceStock.application.dto.request.BrandRequest;
import com.MicroService.MicroServiceStock.application.dto.response.BrandResponse;
import com.MicroService.MicroServiceStock.domain.models.Brand;

import java.util.List;

public interface IBrandHandler {
    void createBrand(BrandRequest brandRequest);
    List<BrandResponse> GetAllBrands();
    BrandResponse getBrandyByName(String name);
    void updateBrand(BrandRequest brandRequest);
    void deleteBrand(String name);
}
