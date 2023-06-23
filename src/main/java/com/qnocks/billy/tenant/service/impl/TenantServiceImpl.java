package com.qnocks.billy.tenant.service.impl;

import com.qnocks.billy.core.exception.custom.TenantException;
import com.qnocks.billy.tenant.dto.CreateTenantDto;
import com.qnocks.billy.tenant.dto.TenantDto;
import com.qnocks.billy.tenant.entity.Tenant;
import com.qnocks.billy.tenant.repository.TenantRepository;
import com.qnocks.billy.tenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    @Override
    public TenantDto createTenant(CreateTenantDto tenantDto) {
        tenantRepository.findByEmail(tenantDto.getEmail()).ifPresent(tenant -> {
            throw TenantException.builder()
                    .message(String.format("Tenant with the email [%s] already exists", tenantDto.getEmail()))
                    .build();
        });

        var tenant = tenantRepository.save(Tenant.builder()
                .name(tenantDto.getName())
                .email(tenantDto.getEmail())
                // TODO: save encrypted password
                .password(tenantDto.getPassword())
                .build());

        return TenantDto.builder()
                .id(tenant.getId())
                .name(tenant.getName())
                .email(tenant.getEmail())
                .createdAt(tenant.getCreatedAt())
                .updatedAt(tenant.getUpdatedAt())
                .build();
    }

    @Override
    public TenantDto getById(String id) {
        var tenant = tenantRepository.findById(id).orElseThrow(() -> TenantException.builder()
                .message(String.format("cannot find Tenant with id [%s]", id))
                .build());

        return TenantDto.builder()
                .id(tenant.getId())
                .name(tenant.getName())
                .email(tenant.getEmail())
                .createdAt(tenant.getCreatedAt())
                .updatedAt(tenant.getUpdatedAt())
                .build();
    }
}
