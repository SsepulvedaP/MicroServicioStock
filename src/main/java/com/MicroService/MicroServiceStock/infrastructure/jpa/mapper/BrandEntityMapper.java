package com.MicroService.MicroServiceStock.infrastructure.jpa.mapper;


import com.MicroService.MicroServiceStock.domain.models.Brand;
import com.MicroService.MicroServiceStock.infrastructure.jpa.entity.BrandEntity;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandEntityMapper {

    BrandEntity toEntity(Brand brand);

    Brand toBrand(BrandEntity brandEntity);

    List<Brand> toListBrand(List<BrandEntity> brandEntityList);
}
