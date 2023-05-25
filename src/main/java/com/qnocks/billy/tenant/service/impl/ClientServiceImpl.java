package com.qnocks.billy.tenant.service.impl;

import com.qnocks.billy.core.exception.custom.TenantException;
import com.qnocks.billy.tenant.dto.ClientDto;
import com.qnocks.billy.tenant.dto.UpdateClientDto;
import com.qnocks.billy.tenant.repository.ClientRepository;
import com.qnocks.billy.tenant.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public ClientDto updateClient(UpdateClientDto clientDto) {
        return null;
    }

    @Override
    public ClientDto getById(Long id) {
        var client = clientRepository.findById(id).orElseThrow(() -> TenantException.builder()
                .message(String.format("cannot find Client with id [%d]", id))
                .build());

        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .tenantId(client.getTenant().getId())
                .build();
    }

    @Override
    public List<ClientDto> getClientsByTenantId(Long tenantId) {
        return clientRepository.findAllByTenantId(tenantId).stream().map(client -> ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .tenantId(client.getTenant().getId())
                .build()).collect(Collectors.toList());
    }
}
