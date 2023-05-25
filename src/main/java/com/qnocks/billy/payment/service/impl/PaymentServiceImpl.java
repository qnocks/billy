package com.qnocks.billy.payment.service.impl;

import com.qnocks.billy.payment.dto.AmountDto;
import com.qnocks.billy.payment.dto.CardDto;
import com.qnocks.billy.payment.dto.ConfirmationDataDto;
import com.qnocks.billy.payment.dto.CreatePaymentDto;
import com.qnocks.billy.payment.dto.PaymentDto;
import com.qnocks.billy.payment.dto.PaymentMethodDataDto;
import com.qnocks.billy.payment.entity.Payment;
import com.qnocks.billy.payment.repository.PaymentRepository;
import com.qnocks.billy.payment.service.PaymentService;
import com.qnocks.billy.subscription.entity.Subscription;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final WebClient webClient;
    private final PaymentRepository paymentRepository;

    @Value("${payment.yookassa.shopId}")
    private String shopId;
    @Value("${payment.yookassa.apiKey}")
    private String apiKey;

    @SneakyThrows
    @Override
    public boolean  pay(Subscription subscription) {
        var createPaymentRef = new AtomicReference<CreatePaymentDto>();
        paymentRepository.findFirstBySubscription_IdOrderByCreatedAtDesc(subscription.getId()). ifPresentOrElse(
                payment -> createPaymentRef.set(buildRecurrentPayment(payment)),
                () -> {
                    // TODO: gather client's bank card credentials
                    createPaymentRef.set(buildNewPayment(subscription, CardDto.builder()
                            .number("5555555555554444")
                            .expiryMonth("05")
                            .expiryYear("2025")
                            .csc("111")
                            .build()));
                });

        var createPaymentDto = createPaymentRef.get();
        var paymentDto = processRequest(createPaymentDto);

        paymentRepository.save(Payment.builder()
                        .externalId(paymentDto.getId())
                        .status(paymentDto.getStatus())
                        .amount(Double.parseDouble(paymentDto.getAmount().getValue()))
                        .currency(paymentDto.getAmount().getCurrency())
                        .incomeAmount(Double.parseDouble(paymentDto.getIncomeAmount().getValue()))
                        .incomeCurrency(paymentDto.getIncomeAmount().getCurrency())
                        .paymentMethodId(paymentDto.getPaymentMethodData().getId())
                        .paymentMethodType(paymentDto.getPaymentMethodData().getType())
                        .externalDate(paymentDto.getCreatedAt())
                        .subscription(subscription)
                .build());
        return true;
    }

    private CreatePaymentDto buildNewPayment(Subscription subscription, CardDto card) {
        return CreatePaymentDto.builder()
                .amount(AmountDto.builder()
                        .value(String.valueOf(subscription.getPrice()))
                        .currency("RUB")
                        .build())
                .paymentMethodData(PaymentMethodDataDto.builder()
                        .type("bank_card")
                        .card(card)
                        .build())
                .confirmation(ConfirmationDataDto.builder()
                        .type("redirect")
                        .returnUrl("https://localhost:8090/api/payment/redirect")
                        .build())
                .savePaymentMethod(true)
                .capture(true)
                .build();
    }

    private CreatePaymentDto buildRecurrentPayment(Payment payment) {
        return CreatePaymentDto.builder()
                .amount(AmountDto.builder()
                        .value(String.valueOf(payment.getSubscription().getPrice()))
                        .currency("RUB")
                        .build())
                .paymentMethodId(payment.getPaymentMethodId())
                .capture(true)
                .build();
    }

    private PaymentDto processRequest(CreatePaymentDto requestBody) {
        var authHeaderValue = "Basic " + Base64.getEncoder().encodeToString((shopId + ":" + apiKey).getBytes());
        return webClient.post()
                .uri("https://api.yookassa.ru/v3/payments")
                .headers(httpHeaders -> {
                    httpHeaders.set("Idempotence-Key", UUID.randomUUID().toString());
                    httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.set(HttpHeaders.AUTHORIZATION, authHeaderValue);
                })
                .body(Mono.just(requestBody), CreatePaymentDto.class)
                .retrieve()
                .bodyToMono(PaymentDto.class)
                .block();
    }
}
