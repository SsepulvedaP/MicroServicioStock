package com.MicroService.MicroServiceStock.domain.usecase;

import com.MicroService.MicroServiceStock.domain.api.IBrandServicePort;
import com.MicroService.MicroServiceStock.domain.exceptions.InvalidBrandDataException;
import com.MicroService.MicroServiceStock.domain.models.Brand;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;
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
            throw new InvalidBrandDataException("La descripción de la marca no puede exceder los 120 caracteres.");
        }
        // Guardar la categoría si todas las validaciones pasan
        brandPersistencePort.createBrand(brand);
    }

    @Override
    public List<Brand> getAllBrands() {
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

    @Override
    public PageCustom<Brand> getBrands(PageRequestCustom pageRequest) {
        // Llamar al puerto de persistencia para obtener las categorías paginadas
        PageCustom<Brand> brandsPage = brandPersistencePort.getBrands(pageRequest);

        // Validar que la respuesta no sea nula y que el contenido no sea nulo o vacío
        if (brandsPage == null || brandsPage.getContent() == null || brandsPage.getContent().isEmpty()) {
            throw new InvalidBrandDataException("No se encontraron marcas.");
        }

        return brandsPage;
    }

}
