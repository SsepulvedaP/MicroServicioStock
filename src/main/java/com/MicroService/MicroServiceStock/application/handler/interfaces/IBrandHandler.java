package com.MicroService.MicroServiceStock.application.handler.interfaces;

import com.MicroService.MicroServiceStock.application.dto.request.BrandRequest;
import com.MicroService.MicroServiceStock.application.dto.response.BrandResponse;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;

import java.util.List;

public interface IBrandHandler {
    void createBrand(BrandRequest brandRequest);
    List<BrandResponse> getAllBrands();
    BrandResponse getBrandyByName(String name);
    void updateBrand(BrandRequest brandRequest);
    void deleteBrand(String name);
    PageCustom<BrandResponse> getBrands(PageRequestCustom pageRequest);
}
