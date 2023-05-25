package com.qnocks.billy.tenant.service;

import com.qnocks.billy.tenant.dto.ClientDto;
import com.qnocks.billy.tenant.dto.UpdateClientDto;

import java.util.List;

public interface ClientService {

    ClientDto updateClient(UpdateClientDto clientDto);
    ClientDto getById(Long id);

    List<ClientDto> getClientsByTenantId(Long tenantId);
    // TODO: пополнитьБаланс(), списатьБаланс(), заблокировать()
}
