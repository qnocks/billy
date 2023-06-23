package com.qnocks.billy.subscription.service;

import com.qnocks.billy.subscription.dto.CreateSubscriptionDto;
import com.qnocks.billy.subscription.dto.SubscriptionDto;

public interface SubscriptionService {

    SubscriptionDto createSubscription(CreateSubscriptionDto subscriptionDto, String tenantId);
    SubscriptionDto updateSubscription(Long id, CreateSubscriptionDto subscriptionDto, String tenantId);
    SubscriptionDto getById(Long id, String tenantId);
    void cancel(Long id, String tenantId);
    void activate(Long id, String tenantId);
    void deleteSubscription(Long id, String tenantId);
}
