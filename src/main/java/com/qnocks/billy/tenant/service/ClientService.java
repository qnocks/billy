package com.qnocks.billy.tenant.service;

import com.qnocks.billy.tenant.dto.ClientDto;
import com.qnocks.billy.tenant.dto.UpdateClientDto;

import java.util.List;

public interface ClientService {

    ClientDto updateClient(UpdateClientDto clientDto);
    ClientDto getById(Long id, String tenantId);

    List<ClientDto> getClientsByTenantId(String tenantId);
    // TODO: пополнитьБаланс(), списатьБаланс(), заблокировать()
}
