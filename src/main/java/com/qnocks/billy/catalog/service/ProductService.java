package com.qnocks.billy.catalog.service;

import com.qnocks.billy.catalog.dto.CreateProductDto;
import com.qnocks.billy.catalog.dto.ProductDto;

public interface ProductService {

    ProductDto createProduct(CreateProductDto productDto);
    ProductDto getById(Long id, String tenantId);
    ProductDto updateProduct(Long id, CreateProductDto productDto, String tenantId);
}
