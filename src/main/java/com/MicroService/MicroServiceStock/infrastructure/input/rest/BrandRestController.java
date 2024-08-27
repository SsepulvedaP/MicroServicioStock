package com.MicroService.MicroServiceStock.infrastructure.input.rest;


import com.MicroService.MicroServiceStock.application.dto.request.BrandRequest;
import com.MicroService.MicroServiceStock.application.dto.response.BrandResponse;
import com.MicroService.MicroServiceStock.application.handler.interfaces.IBrandHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandRestController {

    private final IBrandHandler brandHandler;

    @Operation(summary = "Create a new brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Brand created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/")
    public ResponseEntity<Void> createBrand(@RequestBody BrandRequest brandRequest) {
        brandHandler.createBrand(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Get all brands")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of brands"),
            @ApiResponse(responseCode = "404", description = "No brands found")
    })
    @GetMapping("/")
    public ResponseEntity<List<BrandResponse>> getAllBrands() {
        return ResponseEntity.ok(brandHandler.GetAllBrands());
    }


    @Operation(summary = "Get a brand by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand found successfully"),
            @ApiResponse(responseCode = "404", description = "Brand not found")
    })
    @GetMapping("/{name}")
    public ResponseEntity<BrandResponse> getBrand(@PathVariable String name) {
        BrandResponse brandResponse = brandHandler.getBrandyByName(name);
        return ResponseEntity.ok(brandResponse);
    }


    @Operation(summary = "Update a brand by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand updated successfully"),
            @ApiResponse(responseCode = "404", description = "Brand not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{name}")
    public ResponseEntity<Void> updateBrand(@PathVariable String name, @RequestBody BrandRequest brandRequest) {
        brandHandler.updateBrand(brandRequest);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Delete a brand by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Brand deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Brand not found")
    })
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteBrand(@PathVariable String name) {
        brandHandler.deleteBrand(name);
        return ResponseEntity.noContent().build();
    }

}
