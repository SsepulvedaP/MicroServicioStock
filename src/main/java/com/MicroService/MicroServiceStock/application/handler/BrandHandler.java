package com.MicroService.MicroServiceStock.application.handler;

import com.MicroService.MicroServiceStock.application.dto.request.BrandRequest;
import com.MicroService.MicroServiceStock.application.dto.response.BrandResponse;
import com.MicroService.MicroServiceStock.application.dto.response.CategoryResponse;
import com.MicroService.MicroServiceStock.application.handler.interfaces.IBrandHandler;
import com.MicroService.MicroServiceStock.application.mapper.request.BrandRequestMapper;
import com.MicroService.MicroServiceStock.application.mapper.response.BrandResponseMapper;
import com.MicroService.MicroServiceStock.domain.api.IBrandServicePort;
import com.MicroService.MicroServiceStock.domain.models.Brand;

import com.MicroService.MicroServiceStock.domain.models.Category;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
@Transactional
@RequiredArgsConstructor
public class BrandHandler implements IBrandHandler {

    private final IBrandServicePort brandServicePort;
    private final BrandRequestMapper brandRequestMapper;
    private final BrandResponseMapper brandResponseMapper;


    @Override
    public void createBrand(BrandRequest brandRequest) {
        Brand brand = brandRequestMapper.toBrand(brandRequest);
        brandServicePort.createBrand(brand);
    }


    @Override
    public List<BrandResponse> getAllBrands() {
        return brandResponseMapper.toResponseList(brandServicePort.getAllBrands());
    }

    @Override
    public BrandResponse getBrandyByName(String name) {
        Brand brand = brandServicePort.getBrandByName(name);
        return brandResponseMapper.toResponse(brand);
    }

    @Override
    public void updateBrand(BrandRequest brandRequest) {
        Brand brand = brandRequestMapper.toBrand(brandRequest);
        brandServicePort.updateBrand(brand);

    }

    @Override
    public void deleteBrand(String name) {
        brandServicePort.deleteBrand(name);
    }

    @Override
    public PageCustom<BrandResponse> getBrands(PageRequestCustom pageRequest) {
        PageCustom<Brand> brandsPage = brandServicePort.getBrands(pageRequest);
        List<BrandResponse> responseList = brandResponseMapper.toResponseList(brandsPage.getContent());
        return new PageCustom<>(
                responseList,
                brandsPage.getTotalElements(),
                brandsPage.getTotalPages(),
                brandsPage.getCurrentPage(),
                brandsPage.isAscending()
        );
    }
}
