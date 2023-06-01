package com.qnocks.billy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BillyApplication {

    // TODO: update start when active/cancel subscription
    // TODO: Оплата подписки (баланс клиента)
    // TODO: Мультитенанси (связать все сущности с tenant, изучить как это сделанно у killbill)
    // TODO: Разбить на микросервисы

    public static void main(String[] args) {
        SpringApplication.run(BillyApplication.class, args);
    }
}
