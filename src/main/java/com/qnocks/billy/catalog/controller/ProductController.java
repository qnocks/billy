package com.qnocks.billy.catalog.controller;

import com.qnocks.billy.catalog.dto.CreateProductDto;
import com.qnocks.billy.catalog.dto.ProductDto;
import com.qnocks.billy.catalog.service.ProductService;
import com.qnocks.billy.core.aop.InboundRequest;
import com.qnocks.billy.core.aop.TenantId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "API for tenant's products management")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Create product", description = "Create new tenant's product to billed")
    @PostMapping
    @InboundRequest
    public ProductDto createProduct(@RequestBody CreateProductDto productDto,
                                    @RequestParam @TenantId String tenantId) {
        return productService.createProduct(productDto);
    }

    @Operation(summary = "Get a product", description = "Retrieve product info by provided id")
    @GetMapping("{id}")
    @InboundRequest
    public ProductDto getProduct(@PathVariable Long id,
                                 @RequestParam @TenantId String tenantId) {
        return productService.getById(id, tenantId);
    }

    @Operation(summary = "Update a product", description = "Update product info")
    @PutMapping("{id}")
    @InboundRequest
    public ProductDto updateProduct(@PathVariable Long id,
                                    @RequestBody CreateProductDto productDto,
                                    @RequestParam @TenantId String tenantId) {
        return productService.updateProduct(id, productDto, tenantId);
    }
}
