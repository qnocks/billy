package com.qnocks.billy.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private String id;
    private String status;
    private AmountDto amount;

    @JsonProperty("income_amount")
    private AmountDto incomeAmount;
    private ConfirmationDataDto confirmation;

    @JsonProperty("payment_method")
    private PaymentMethodDataDto paymentMethodData;
    private String description;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
