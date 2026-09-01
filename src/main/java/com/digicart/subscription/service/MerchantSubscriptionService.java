package com.digicart.subscription.service;

import com.digicart.subscription.dto.MerchantSubscriptionDto;
import com.digicart.subscription.entity.MerchantSubscription;
import com.digicart.subscription.entity.SubscriptionPlan;
import com.digicart.subscription.exception.EntityNotFoundException;
import com.digicart.subscription.repository.MerchantSubscriptionRepository;
import com.digicart.subscription.repository.SubscriptionPlanRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

@Service
public class MerchantSubscriptionService {

    private final MerchantSubscriptionRepository merchantRepo;
    private final SubscriptionPlanRepository planRepo;

    public MerchantSubscriptionService(MerchantSubscriptionRepository merchantRepo,
                                       SubscriptionPlanRepository planRepo) {
        this.merchantRepo = merchantRepo;
        this.planRepo = planRepo;
    }

    public Map<String, Object> getStatus(String merchantEmail) {
        MerchantSubscription ms = merchantRepo.findByMerchantEmail(merchantEmail).orElse(null);

        if (ms == null || ms.getPlanId() == null) {
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("subscribed", false);
            result.put("availableDays", ms != null ? (ms.getAvailableDays() != null ? ms.getAvailableDays() : 0) : 0);
            result.put("expired", false);
            result.put("subscription", null);
            return result;
        }

        int availableDays = ms.getAvailableDays() != null ? ms.getAvailableDays() : 0;
        boolean expired = availableDays <= 0;

        SubscriptionPlan plan = planRepo.findById(ms.getPlanId()).orElse(null);
        Map<String, Object> planMap = null;
        if (plan != null) {
            planMap = new LinkedHashMap<>();
            planMap.put("name", plan.getName());
            planMap.put("price", plan.getPrice());
            planMap.put("currency", plan.getCurrency());
            planMap.put("billingPeriod", plan.getBillingPeriod() != null ? plan.getBillingPeriod().name() : null);
            planMap.put("maxProducts", plan.getMaxProducts());
            planMap.put("features", plan.getFeatures() != null ? plan.getFeatures() : Map.of());
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("subscribed", true);
        result.put("availableDays", availableDays);
        result.put("expired", expired);
        result.put("subscription", planMap);
        return result;
    }

    public MerchantSubscription assign(MerchantSubscriptionDto.AssignRequest req) {
        if (req.merchantEmail() == null || req.merchantEmail().isBlank()) {
            throw new IllegalArgumentException("merchantEmail is required");
        }
        if (req.planId() != null && !req.planId().isBlank() && !planRepo.existsById(req.planId())) {
            throw new EntityNotFoundException("Subscription plan not found: " + req.planId());
        }

        MerchantSubscription ms = merchantRepo.findByMerchantEmail(req.merchantEmail())
                .orElseGet(() -> {
                    MerchantSubscription n = new MerchantSubscription();
                    n.setMerchantEmail(req.merchantEmail());
                    return n;
                });

        if (req.planId() != null) {
            ms.setPlanId(req.planId().isBlank() ? null : req.planId());
            ms.setSubscribedAt(Instant.now());
        }
        if (req.renewsAt() != null) ms.setRenewsAt(req.renewsAt());
        if (req.availableDays() != null) ms.setAvailableDays(req.availableDays());
        return merchantRepo.save(ms);
    }

    public List<MerchantSubscription> findAll() {
        return merchantRepo.findAll();
    }

    public MerchantSubscription findByEmail(String email) {
        return merchantRepo.findByMerchantEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Merchant subscription not found: " + email));
    }
}
