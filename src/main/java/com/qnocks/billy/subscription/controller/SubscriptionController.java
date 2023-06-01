package com.qnocks.billy.subscription.controller;

import com.qnocks.billy.core.aop.InboundRequest;
import com.qnocks.billy.core.aop.TenantId;
import com.qnocks.billy.subscription.dto.CreateSubscriptionDto;
import com.qnocks.billy.subscription.dto.SubscriptionDto;
import com.qnocks.billy.subscription.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscription", description = "API for tenant's subscriptions management")
public class
SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Operation(summary = "Create a subscription", description = "Create new tenant's subscription")
    @PostMapping
    @InboundRequest
    public SubscriptionDto createSubscription(@RequestBody CreateSubscriptionDto subscriptionDto,
                                              @RequestParam @TenantId String tenantId) {
        return subscriptionService.createSubscription(subscriptionDto, tenantId);
    }

    @Operation(summary = "Get the subscription", description = "Retrieve subscription info by provided id")
    @GetMapping("{id}")
    @InboundRequest
    public SubscriptionDto getSubscription(@PathVariable Long id, @RequestParam @TenantId String tenantId) {
        return subscriptionService.getById(id, tenantId);
    }

    @Operation(summary = "Update the subscription", description = "Update subscription info")
    @PutMapping("{id}")
    @InboundRequest
    public SubscriptionDto updateSubscription(
            @PathVariable Long id,
            @RequestBody CreateSubscriptionDto subscriptionDto,
            @RequestParam @TenantId String tenantId) {
        return subscriptionService.updateSubscription(id, subscriptionDto, tenantId);
    }

    @Operation(summary = "Cancel the subscription", description = "Cancel specific subscription")
    @PatchMapping("{id}")
    @InboundRequest
    public void cancelSubscription(@PathVariable Long id, @RequestParam @TenantId String tenantId) {
        subscriptionService.cancel(id, tenantId);
    }

    @Operation(summary = "Activate the subscription", description = "Activate specific subscription")
    @PostMapping("{id}")
    @InboundRequest
    public void activateSubscription(@PathVariable Long id, @RequestParam @TenantId String tenantId) {
        subscriptionService.activate(id, tenantId);
    }

    @Operation(summary = "Delete the subscription", description = "Delete specific subscription")
    @DeleteMapping("{id}")
    @InboundRequest
    public void deleteSubscription(@PathVariable Long id, @RequestParam @TenantId String tenantId) {
        subscriptionService.deleteSubscription(id, tenantId);
    }
}
