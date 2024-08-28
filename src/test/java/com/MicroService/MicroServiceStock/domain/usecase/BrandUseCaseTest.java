package com.MicroService.MicroServiceStock.domain.usecase;

import com.MicroService.MicroServiceStock.domain.exceptions.InvalidBrandDataException;
import com.MicroService.MicroServiceStock.domain.models.Brand;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;
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

        List<Brand> result = brandUseCase.getAllBrands();

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

    @Test
    void getBrands_ShouldReturnBrandsPage_WhenBrandsExist() {
        // Datos de prueba
        PageRequestCustom pageRequest = new PageRequestCustom(0, 10, true, "name");
        PageCustom<Brand> expectedPage = new PageCustom<>();

        // Agrega marcas a expectedPage si es necesario
        List<Brand> mockBrands = Arrays.asList(
                new Brand(1L, "Marca A", "Descripción A"),
                new Brand(2L, "Marca B", "Descripción B")
        );
        expectedPage.setContent(mockBrands);
        expectedPage.setTotalElements(2);
        expectedPage.setTotalPages(1);

        // Configuración del comportamiento esperado
        when(brandPersistencePort.getBrands(pageRequest)).thenReturn(expectedPage);

        // Ejecución del metodo
        PageCustom<Brand> result = brandUseCase.getBrands(pageRequest);

        // Verificación
        assertEquals(expectedPage, result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(mockBrands, result.getContent());
        verify(brandPersistencePort).getBrands(pageRequest);
    }

    @Test
    void getBrands_ShouldThrowException_WhenContentIsNull() {
        // Datos de prueba
        PageRequestCustom pageRequest = new PageRequestCustom(0, 10, true, "name");

        // Configuración del comportamiento esperado
        PageCustom<Brand> brandsPage = new PageCustom<>();
        brandsPage.setContent(null);
        when(brandPersistencePort.getBrands(pageRequest)).thenReturn(brandsPage);

        // Ejecución y verificación de la excepción
        InvalidBrandDataException exception = assertThrows(InvalidBrandDataException.class, () -> {
            brandUseCase.getBrands(pageRequest);
        });

        assertEquals("No se encontraron marcas.", exception.getMessage());
        verify(brandPersistencePort).getBrands(pageRequest);
    }

}
