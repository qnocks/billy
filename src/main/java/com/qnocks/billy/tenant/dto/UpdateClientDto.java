package com.qnocks.billy.tenant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientDto {

    private Long id;
    private String name;
    private String email;
    private String tenantId;
}
