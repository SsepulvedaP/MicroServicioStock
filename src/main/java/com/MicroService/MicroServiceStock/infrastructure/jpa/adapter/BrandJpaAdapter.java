package com.MicroService.MicroServiceStock.infrastructure.jpa.adapter;

import com.MicroService.MicroServiceStock.domain.exceptions.DuplicateBrandNameException;
import com.MicroService.MicroServiceStock.domain.models.Brand;
import com.MicroService.MicroServiceStock.domain.spi.IBrandPersistencePort;
import com.MicroService.MicroServiceStock.infrastructure.exception.BrandNotFoundException;
import com.MicroService.MicroServiceStock.infrastructure.exception.NoDataFoundException;
import com.MicroService.MicroServiceStock.infrastructure.jpa.entity.BrandEntity;
import com.MicroService.MicroServiceStock.infrastructure.jpa.mapper.BrandEntityMapper;
import com.MicroService.MicroServiceStock.infrastructure.jpa.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BrandJpaAdapter implements IBrandPersistencePort {

    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;


    @Override
    public void createBrand(Brand brand) {
        if(brandRepository.findByName(brand.getName()).isPresent()){
            throw new DuplicateBrandNameException(brand.getName());
        }
        BrandEntity brandEntity = brandEntityMapper.toEntity(brand);
        brandRepository.save(brandEntity);

    }

    @Override
    public List<Brand> getAllBrands() {
        List<BrandEntity> brandEntityList = brandRepository.findAll();
        if(brandEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return brandEntityMapper.toListBrand(brandEntityList);
    }


    @Override
    public Brand getBrandByName(String name) {
        return brandEntityMapper.toBrand
                (brandRepository.findByName(name)
                        .orElseThrow(BrandNotFoundException::new));

    }

    @Override
    public void updateBrand(Brand brand) {
        brandRepository.save(brandEntityMapper.toEntity(brand));

    }

    @Override
    public void deleteBrand(String name) {
        brandRepository.deleteByName(name);

    }
}
