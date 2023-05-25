package com.qnocks.billy.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubscriptionDto {

    private Long clientId;
    private Long productId;
    private String startDate;
    private String period;
    private Double discount;
    private String discountType;
}
