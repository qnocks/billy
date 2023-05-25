package com.qnocks.billy.flow.service.impl;

import com.qnocks.billy.core.exception.custom.PaymentException;
import com.qnocks.billy.flow.service.FlowService;
import com.qnocks.billy.payment.service.PaymentService;
import com.qnocks.billy.subscription.repository.SubscriptionRepository;
import com.qnocks.billy.subscription.type.SubscriptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlowServiceImpl implements FlowService {

    private final SubscriptionRepository subscriptionRepository;
    private final PaymentService paymentService;

    @Scheduled(initialDelay = 3000, fixedDelay = 5000)
    @Override
    public void checkForSubscription() {
        var subscriptions = subscriptionRepository.findAll().stream()
                .filter(subscription -> subscription.getStatus().equals(SubscriptionStatus.ACTIVE))
                .filter(subscription -> subscription.getNext().equals(LocalDate.now()))
                .collect(Collectors.toList());

        // TODO: multithreading
        subscriptions.forEach(subscription -> {
            paymentService.pay(subscription);
//            if (!paymentSuccess) {
//                throw PaymentException.builder()
//                        .message(String.format("there's payment error with subscription [%s]", subscription.getId()))
//                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .build();
//            }

            subscription.setNext(subscription.getNext().plusDays(subscription.getPeriodicity().getValue()));
            subscriptionRepository.save(subscription);
        });
    }
}
