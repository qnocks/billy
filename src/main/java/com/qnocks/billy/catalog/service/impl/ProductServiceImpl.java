package com.qnocks.billy.catalog.service.impl;

import com.qnocks.billy.catalog.dto.CreateProductDto;
import com.qnocks.billy.catalog.dto.ProductDto;
import com.qnocks.billy.catalog.entity.Product;
import com.qnocks.billy.catalog.repository.ProductRepository;
import com.qnocks.billy.catalog.service.ProductService;
import com.qnocks.billy.core.exception.custom.ProductException;
import com.qnocks.billy.core.exception.custom.TenantException;
import com.qnocks.billy.tenant.entity.Tenant;
import com.qnocks.billy.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final TenantRepository tenantRepository;

    @Override
    public ProductDto createProduct(CreateProductDto productDto) {
        var tenant = getTenant(productDto.getTenantId());

        var product = productRepository.save(Product.builder()
                .name(productDto.getName())
                .definition(productDto.getDefinition())
                .price(productDto.getPrice())
                .tenant(tenant)
                .build());

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .definition(product.getDefinition())
                .price(product.getPrice())
                .tenantId(product.getTenant().getId())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    @Override
    public ProductDto getById(Long id, String tenantId) {
        var product = getProduct(id, tenantId);

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .definition(product.getDefinition())
                .price(product.getPrice())
                .tenantId(product.getTenant().getId())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    @Override
    public ProductDto updateProduct(Long id, CreateProductDto productDto, String tenantId) {
        var existingProduct = getProduct(id, tenantId);
        var tenant = getTenant(tenantId);

        existingProduct.setName(productDto.getName());
        existingProduct.setDefinition(productDto.getDefinition());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setTenant(tenant);

        productRepository.saveAndFlush(existingProduct);
        return ProductDto.builder()
                .id(existingProduct.getId())
                .name(existingProduct.getName())
                .definition(existingProduct.getDefinition())
                .price(existingProduct.getPrice())
                .tenantId(existingProduct.getTenant().getId())
                .createdAt(existingProduct.getCreatedAt())
                .updatedAt(existingProduct.getUpdatedAt())
                .build();
    }

    private Product getProduct(Long id, String tenantId) {
        return productRepository.findByIdAndTenantId(id, tenantId).orElseThrow(() -> ProductException.builder()
                .message(String.format("cannot find Product with id [%d]", id))
                .status(HttpStatus.NOT_FOUND)
                .build());
    }

    private Tenant getTenant(String id) {
        return tenantRepository.findById(id).orElseThrow(() -> TenantException.builder()
                .message(String.format("cannot find Tenant with id [%s]", id))
                .status(HttpStatus.NOT_FOUND)
                .build());
    }
}
