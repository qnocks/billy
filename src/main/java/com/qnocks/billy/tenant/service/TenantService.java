package com.qnocks.billy.tenant.service;

import com.qnocks.billy.tenant.dto.CreateTenantDto;
import com.qnocks.billy.tenant.dto.TenantDto;

public interface TenantService {

    TenantDto createTenant(CreateTenantDto tenantDto);
    TenantDto getById(Long id);
}
