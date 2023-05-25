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
public class ConfirmationDataDto {

    private String type = "redirect";

    @JsonProperty("return_url")
    private String returnUrl;

    @JsonProperty("confirmation_url")
    private String confirmationUrl;
}
