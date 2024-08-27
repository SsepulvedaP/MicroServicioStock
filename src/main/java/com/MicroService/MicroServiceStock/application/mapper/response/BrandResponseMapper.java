package com.MicroService.MicroServiceStock.application.mapper.response;


import com.MicroService.MicroServiceStock.application.dto.response.BrandResponse;
import com.MicroService.MicroServiceStock.domain.models.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandResponseMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    BrandResponse toResponse(Brand brand);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    List<BrandResponse> toResponseList(List<Brand> brands);
}
