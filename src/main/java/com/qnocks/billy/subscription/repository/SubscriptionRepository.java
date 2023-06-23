package com.qnocks.billy.subscription.repository;

import com.qnocks.billy.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

//    Optional<Subscription> findByProduct_IdAndAndClient_Id(Long productId, Long clientId);
    Optional<Subscription> findByIdAndTenantId(Long id, String tenantId);
    Optional<Subscription> findByProductIdAndAndClientIdAndTenantId(Long productId, Long clientId, String tenantId);

    void deleteByIdAndTenantId(Long id, String tenantId);
}
