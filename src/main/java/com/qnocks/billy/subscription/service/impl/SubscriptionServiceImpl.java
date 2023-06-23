package com.qnocks.billy.subscription.service.impl;

import com.qnocks.billy.catalog.entity.Product;
import com.qnocks.billy.catalog.repository.ProductRepository;
import com.qnocks.billy.core.exception.custom.ProductException;
import com.qnocks.billy.core.exception.custom.SubscriptionException;
import com.qnocks.billy.core.exception.custom.TenantException;
import com.qnocks.billy.subscription.dto.CreateSubscriptionDto;
import com.qnocks.billy.subscription.dto.SubscriptionDto;
import com.qnocks.billy.subscription.entity.Subscription;
import com.qnocks.billy.subscription.repository.SubscriptionRepository;
import com.qnocks.billy.subscription.service.SubscriptionService;
import com.qnocks.billy.subscription.type.DiscountType;
import com.qnocks.billy.subscription.type.Periodicity;
import com.qnocks.billy.subscription.type.SubscriptionStatus;
import com.qnocks.billy.tenant.repository.ClientRepository;
import com.qnocks.billy.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final TenantRepository tenantRepository;

    @Override
    public SubscriptionDto createSubscription(CreateSubscriptionDto subscriptionDto, String tenantId) {
        var client = clientRepository.findByIdAndTenantId(subscriptionDto.getClientId(), tenantId).orElseThrow(
                () -> TenantException.builder()
                        .message(String.format("cannot find Client with id [%d]", subscriptionDto.getClientId()))
                        .status(HttpStatus.NOT_FOUND)
                        .build());

        var product = productRepository.findByIdAndTenantId(subscriptionDto.getProductId(), tenantId).orElseThrow(
                () -> ProductException.builder()
                        .message(String.format("cannot find Product with id [%d]", subscriptionDto.getClientId()))
                        .status(HttpStatus.NOT_FOUND)
                        .build());

        var tenant = tenantRepository.findById(tenantId).orElseThrow(() -> TenantException.builder()
                .message(String.format("cannot find Product with id [%s]", tenantId))
                .status(HttpStatus.NOT_FOUND)
                .build());

        subscriptionRepository.findByProductIdAndAndClientIdAndTenantId(product.getId(), client.getId(), tenant.getId())
                .ifPresent(subscription -> {
                    throw SubscriptionException.builder()
                            .message("Subscription with provided ids already exists")
                            .build();
                });

        // TODO: change to more readable
        if (subscriptionDto.getDiscountType() == null) {
            subscriptionDto.setDiscountType("PERCENT");
        }
        if (subscriptionDto.getPeriod() == null) {
            subscriptionDto.setPeriod("MONTH");
        }

        var subscription = Subscription.builder()
                .client(client)
                .product(product)
                .tenant(tenant)
                .start(LocalDate.parse(subscriptionDto.getStartDate()))
                .next(LocalDate.parse(subscriptionDto.getStartDate()))
                .periodicity(Periodicity.valueOf(subscriptionDto.getPeriod()))
                .status(SubscriptionStatus.ACTIVE)
                .discount(subscriptionDto.getDiscount())
                .discountType(DiscountType.valueOf(subscriptionDto.getDiscountType()))
                .build();
        subscription.setPrice(getTotalPrice(product, subscription));

        subscriptionRepository.saveAndFlush(subscription);

        return SubscriptionDto.builder()
                .id(subscription.getId())
                .clientId(subscription.getClient().getId())
                .productId(subscription.getProduct().getId())
                .startDate(subscription.getStart().toString())
                .status(subscription.getStatus().name())
                .period(subscription.getPeriodicity().name())
                .totalPrice(subscription.getPrice())
                .discount(subscription.getDiscount())
                .discountType(subscription.getDiscountType().name())
                .createdAt(subscription.getCreatedAt())
                .updatedAt(subscription.getUpdatedAt())
                .build();
    }

    @Override
    public SubscriptionDto updateSubscription(Long id, CreateSubscriptionDto subscriptionDto, String tenantId) {
        return null;
    }

    @Override
    public SubscriptionDto getById(Long id, String tenantId) {
        var subscription = getSubscription(id, tenantId);

        return SubscriptionDto.builder()
                .id(subscription.getId())
                .clientId(subscription.getClient().getId())
                .startDate(subscription.getStart().toString())
                .productId(subscription.getProduct().getId())
                .status(subscription.getStatus().name())
                .period(subscription.getPeriodicity().name())
                .totalPrice(getTotalPrice(subscription.getProduct(), subscription))
                .discount(subscription.getDiscount())
                .discountType(subscription.getDiscountType().name())
                .createdAt(subscription.getCreatedAt())
                .updatedAt(subscription.getUpdatedAt())
                .build();
    }

    @Override
    public void cancel(Long id, String tenantId) {
        var subscription = getSubscription(id, tenantId);
        subscription.setStatus(SubscriptionStatus.DISABLE);
        subscriptionRepository.save(subscription);
    }

    @Override
    public void activate(Long id, String tenantId) {
        var subscription = getSubscription(id, tenantId);
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscriptionRepository.save(subscription);
    }

    @Override
    public void deleteSubscription(Long id, String tenantId) {
        subscriptionRepository.deleteByIdAndTenantId(id, tenantId);
    }

    private Double getTotalPrice(Product product, Subscription subscription) {
        if (subscription.getDiscountType().equals(DiscountType.CURRENCY)) {
            return product.getPrice() - subscription.getDiscount();
        }

        return product.getPrice() - (product.getPrice() * subscription.getDiscount()) / 100;
    }

    private Subscription getSubscription(Long id, String tenantId) {
        return subscriptionRepository.findByIdAndTenantId(id, tenantId).orElseThrow(() -> SubscriptionException.builder()
                .message(String.format("cannot find Subscription with id [%d]", id))
                .status(HttpStatus.NOT_FOUND)
                .build());
    }
}
