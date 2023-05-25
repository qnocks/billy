package com.qnocks.billy.subscription.service;

import com.qnocks.billy.subscription.dto.CreateSubscriptionDto;
import com.qnocks.billy.subscription.dto.SubscriptionDto;

public interface SubscriptionService {

    SubscriptionDto createSubscription(CreateSubscriptionDto subscriptionDto);
    SubscriptionDto updateSubscription(Long id, CreateSubscriptionDto subscriptionDto);
    SubscriptionDto getById(Long id);
    void cancel(Long id);
    void activate(Long id);
    void deleteSubscription(Long id);
}
