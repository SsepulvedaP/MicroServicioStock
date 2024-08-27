package com.MicroService.MicroServiceStock.domain.usecase;

import com.MicroService.MicroServiceStock.domain.models.Brand;
import com.MicroService.MicroServiceStock.domain.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBrand() {
        Brand brand = new Brand(1L, "BrandName", "This is a description of the brand.");
        doNothing().when(brandPersistencePort).createBrand(brand);

        brandUseCase.createBrand(brand);

        verify(brandPersistencePort, times(1)).createBrand(brand);
    }

    @Test
    void getAllBrands() {
        List<Brand> brands = Arrays.asList(
                new Brand(1L, "BrandName", "This is a description of the brand."),
                new Brand(2L, "Apple", "This is a description of the brand.")
        );
        when(brandPersistencePort.getAllBrands()).thenReturn(brands);

        List<Brand> result = brandUseCase.GetAllBrands();

        assertEquals(2, result.size());
        verify(brandPersistencePort, times(1)).getAllBrands();
    }

    @Test
    void getBrandByName() {
        Brand brand = new Brand(1L, "BrandName", "This is a description of the brand.");
        when(brandPersistencePort.getBrandByName("BrandName")).thenReturn(brand);

        Brand result = brandUseCase.getBrandByName("BrandName");

        assertNotNull(result);
        assertEquals("BrandName", result.getName());
        verify(brandPersistencePort, times(1)).getBrandByName("BrandName");
    }

    @Test
    void updateBrand() {
        Brand brand = new Brand(1L, "BrandName", "This is a description of the brand.");
        doNothing().when(brandPersistencePort).updateBrand(brand);

        brandUseCase.updateBrand(brand);

        verify(brandPersistencePort, times(1)).updateBrand(brand);
    }

    @Test
    void deleteBrand() {
        doNothing().when(brandPersistencePort).deleteBrand("BrandName");

        brandUseCase.deleteBrand("BrandName");

        verify(brandPersistencePort, times(1)).deleteBrand("BrandName");
    }
}
