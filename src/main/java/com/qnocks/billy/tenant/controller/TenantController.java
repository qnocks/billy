package com.qnocks.billy.tenant.controller;

import com.qnocks.billy.tenant.dto.CreateTenantDto;
import com.qnocks.billy.tenant.dto.TenantDto;
import com.qnocks.billy.tenant.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
@Tag(name = "Tenant", description = "API for tenant management")
public class TenantController {

    private final TenantService tenantService;

    @Operation(summary = "Create tenant account", description = "Create new company account")
    @PostMapping
    public TenantDto createTenant(@RequestBody CreateTenantDto tenantDto) {
        return tenantService.createTenant(tenantDto);
    }

    @Operation(summary = "Get a tenant", description = "Retrieve tenant info by provided id")
    @GetMapping("{id}")
    public TenantDto getTenant(@PathVariable Long id) {
        return tenantService.getById(id);
    }
}
