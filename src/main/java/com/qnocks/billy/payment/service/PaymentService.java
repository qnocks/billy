package com.qnocks.billy.payment.service;

import com.qnocks.billy.subscription.entity.Subscription;
import org.springframework.web.servlet.view.RedirectView;

public interface PaymentService {

    boolean pay(Subscription subscription);
}
