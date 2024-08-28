package com.MicroService.MicroServiceStock.domain.spi;

import com.MicroService.MicroServiceStock.domain.models.Brand;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;

import java.util.List;

public interface IBrandPersistencePort {
    void createBrand(Brand brand);
    List<Brand> getAllBrands();
    Brand getBrandByName(String name);
    void updateBrand(Brand brand);
    void deleteBrand(String name);
    PageCustom<Brand> getBrands(PageRequestCustom pageRequest);
}
