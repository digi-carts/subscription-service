package com.digicart.subscription.repository;

import com.digicart.subscription.entity.MerchantSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantSubscriptionRepository extends JpaRepository<MerchantSubscription, String> {
    Optional<MerchantSubscription> findByMerchantEmail(String merchantEmail);
}
