package com.qnocks.billy.payment.repository;

import com.qnocks.billy.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

//    Optional<Payment> findBySubscription_Id(Long id);
    Optional<Payment> findFirstBySubscription_IdOrderByCreatedAtDesc(Long id);
}
