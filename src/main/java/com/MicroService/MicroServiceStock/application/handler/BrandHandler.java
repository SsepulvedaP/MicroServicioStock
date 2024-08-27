package com.MicroService.MicroServiceStock.application.handler;

import com.MicroService.MicroServiceStock.application.dto.request.BrandRequest;
import com.MicroService.MicroServiceStock.application.dto.response.BrandResponse;
import com.MicroService.MicroServiceStock.application.handler.interfaces.IBrandHandler;
import com.MicroService.MicroServiceStock.application.mapper.request.BrandRequestMapper;
import com.MicroService.MicroServiceStock.application.mapper.response.BrandResponseMapper;
import com.MicroService.MicroServiceStock.domain.api.IBrandServicePort;
import com.MicroService.MicroServiceStock.domain.models.Brand;

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
    public List<BrandResponse> GetAllBrands() {
        return brandResponseMapper.toResponseList(brandServicePort.GetAllBrands());
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
        Brand brand = brandServicePort.getBrandByName(name);
        brandServicePort.deleteBrand(name);
    }
}
