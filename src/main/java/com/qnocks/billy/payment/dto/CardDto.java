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
public class CardDto {

    private String number;

    @JsonProperty("expiry_year")
    private String expiryYear;

    @JsonProperty("expiry_month")
    private String expiryMonth;
    private String csc;
}
