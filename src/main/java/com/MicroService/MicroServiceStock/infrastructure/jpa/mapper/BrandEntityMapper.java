package com.MicroService.MicroServiceStock.infrastructure.jpa.mapper;


import com.MicroService.MicroServiceStock.domain.models.Brand;
import com.MicroService.MicroServiceStock.infrastructure.jpa.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandEntityMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    BrandEntity toEntity(Brand brand);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Brand toBrand(BrandEntity brandEntity);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    List<Brand> toListBrand(List<BrandEntity> brandEntityList);
}
