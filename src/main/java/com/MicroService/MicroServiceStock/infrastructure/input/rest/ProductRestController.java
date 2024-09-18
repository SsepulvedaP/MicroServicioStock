package com.MicroService.MicroServiceStock.infrastructure.input.rest;


import com.MicroService.MicroServiceStock.application.dto.request.ProductRequest;
import com.MicroService.MicroServiceStock.application.dto.request.UpdateProductRequest;
import com.MicroService.MicroServiceStock.application.dto.response.ProductResponse;
import com.MicroService.MicroServiceStock.application.handler.interfaces.IProductHandler;
import com.MicroService.MicroServiceStock.domain.pagination.PageCustom;
import com.MicroService.MicroServiceStock.domain.pagination.PageRequestCustom;
import com.MicroService.MicroServiceStock.infrastructure.exception.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final IProductHandler productHandler;

    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada incorrectos",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> createProduct(@RequestBody ProductRequest productRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        productHandler.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos encontrados.",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "No hay productos encontrados.",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productHandler.getAllProducts());
    }

    @Operation(summary = "Get a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getArticleById(@PathVariable Long id) {
        return productHandler.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));
    }

    @Operation(summary = "Get paginated list of products with sorting and filtering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos encontrados",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "No hay productos encontrados",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/paged")
    public ResponseEntity<PageCustom<ProductResponse>> getProductsPaged(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "name") String sortField,
            @RequestParam(required = false, defaultValue = "true") boolean ascending,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String categoryName) {

        PageRequestCustom pageRequest = new PageRequestCustom(page, size, ascending, sortField);
        PageCustom<ProductResponse> articles = productHandler.getProducts(pageRequest, brandName, categoryName);

        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PatchMapping("/updateStock")
    @PreAuthorize("hasAuthority('ROLE_AUX_BODEGA')")
    public ResponseEntity<Void> updateProductStock(@Valid @RequestBody UpdateProductRequest updateProductRequest) {
        productHandler.updateProduct(updateProductRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
