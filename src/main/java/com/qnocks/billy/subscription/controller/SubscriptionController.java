package com.qnocks.billy.subscription.controller;

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
    public SubscriptionDto createSubscription(@RequestBody CreateSubscriptionDto subscriptionDto) {
        return subscriptionService.createSubscription(subscriptionDto);
    }

    @Operation(summary = "Get the subscription", description = "Retrieve subscription info by provided id")
    @GetMapping("{id}")
    public SubscriptionDto getSubscription(@PathVariable Long id) {
        return subscriptionService.getById(id);
    }

    @Operation(summary = "Update the subscription", description = "Update subscription info")
    @PutMapping("{id}")
    public SubscriptionDto updateSubscription(
            @PathVariable Long id,
            @RequestBody CreateSubscriptionDto subscriptionDto) {
        return subscriptionService.updateSubscription(id, subscriptionDto);
    }

    @Operation(summary = "Cancel the subscription", description = "Cancel specific subscription")
    @PatchMapping("{id}")
    public void cancelSubscription(@PathVariable Long id) {
        subscriptionService.cancel(id);
    }

    @Operation(summary = "Activate the subscription", description = "Activate specific subscription")
    @PostMapping("{id}")
    public void activateSubscription(@PathVariable Long id) {
        subscriptionService.activate(id);
    }

    @Operation(summary = "Delete the subscription", description = "Delete specific subscription")
    @DeleteMapping("{id}")
    public void deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
    }
}
