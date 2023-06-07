package com.qnocks.billy.tenant.controller;

import com.qnocks.billy.core.aop.TenantRelatedRequest;
import com.qnocks.billy.core.aop.TenantId;
import com.qnocks.billy.tenant.dto.ClientDto;
import com.qnocks.billy.tenant.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Client", description = "API for tenant's clients management")
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Get a client", description = "Retrieve client info by provided id")
    @GetMapping("{id}")
    @TenantRelatedRequest
    public ClientDto getClient(@PathVariable Long id, @RequestParam @TenantId String tenantId) {
        return clientService.getById(id, tenantId);
    }

    @Operation(summary = "Get clients", description = "Retrieve all tenant's clients by tenant ID")
    @GetMapping
    @TenantRelatedRequest
    public List<ClientDto> getClients(@RequestParam @TenantId String tenantId) {
        return clientService.getClientsByTenantId(tenantId);
    }
}
