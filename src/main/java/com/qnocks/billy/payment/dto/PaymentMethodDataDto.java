package com.qnocks.billy.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDataDto {

    private String id;
    private String type;
    private CardDto card;
    private Boolean saved;
}
