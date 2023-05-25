package com.qnocks.billy.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentDto {

    private AmountDto amount;

    @JsonProperty("payment_method_data")
    private PaymentMethodDataDto paymentMethodData;

    @JsonProperty("payment_method_id")
    private String paymentMethodId;

    private ConfirmationDataDto confirmation;

    @JsonProperty("save_payment_method")
    private Boolean savePaymentMethod = true;
    private Boolean capture = true;
}
