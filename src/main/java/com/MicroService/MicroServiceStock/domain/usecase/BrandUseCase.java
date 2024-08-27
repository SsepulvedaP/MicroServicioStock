package com.MicroService.MicroServiceStock.domain.usecase;

import com.MicroService.MicroServiceStock.domain.api.IBrandServicePort;
import com.MicroService.MicroServiceStock.domain.exceptions.InvalidBrandDataException;
import com.MicroService.MicroServiceStock.domain.models.Brand;
import com.MicroService.MicroServiceStock.domain.spi.IBrandPersistencePort;

import java.util.List;

public class BrandUseCase implements IBrandServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void createBrand(Brand brand) {
        // Validación: El nombre no debe exceder 50 caracteres
        if (brand.getName().length() > 50 ) {
            throw new InvalidBrandDataException("El nombre de la marca no puede exceder los 50 caracteres.");
        }

        // Validación: La descripción no debe exceder 120 caracteres
        if (brand.getDescription().length() > 120) {
            throw new InvalidBrandDataException("La descripción de la marca no puede exceder los 90 caracteres.");
        }
        // Guardar la categoría si todas las validaciones pasan
        brandPersistencePort.createBrand(brand);
    }

    @Override
    public List<Brand> GetAllBrands() {
        return brandPersistencePort.getAllBrands();
    }

    @Override
    public Brand getBrandByName(String name) {
        return brandPersistencePort.getBrandByName(name);
    }

    @Override
    public void updateBrand(Brand brand) {
         brandPersistencePort.updateBrand(brand);

    }

    @Override
    public void deleteBrand(String name) {
        brandPersistencePort.deleteBrand(name);

    }
}
