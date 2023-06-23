package com.qnocks.billy.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String definition;
    private Double price;
    private String tenantId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
