package com.digicart.subscription.service;

import com.digicart.subscription.dto.SubscriptionPlanDto;
import com.digicart.subscription.entity.BillingPeriod;
import com.digicart.subscription.entity.SubscriptionPlan;
import com.digicart.subscription.exception.EntityNotFoundException;
import com.digicart.subscription.repository.SubscriptionPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository repo;

    public SubscriptionPlanService(SubscriptionPlanRepository repo) {
        this.repo = repo;
    }

    public List<SubscriptionPlan> findAll() {
        return repo.findAll();
    }

    public SubscriptionPlan findById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subscription plan not found: " + id));
    }

    public SubscriptionPlan create(SubscriptionPlanDto.CreateRequest req) {
        if (req.name() == null || req.name().isBlank()) {
            throw new IllegalArgumentException("Plan name is required");
        }
        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setName(req.name().trim());
        if (req.maxProducts() != null) plan.setMaxProducts(req.maxProducts());
        if (req.price() != null) plan.setPrice(req.price());
        if (req.currency() != null && !req.currency().isBlank()) plan.setCurrency(req.currency());
        if (req.billingPeriod() != null) plan.setBillingPeriod(BillingPeriod.valueOf(req.billingPeriod()));
        if (req.customDays() != null) plan.setCustomDays(req.customDays());
        if (req.features() != null) plan.setFeatures(req.features());
        if (req.details() != null) plan.setDetails(req.details());
        if (req.level() != null && !req.level().isBlank()) plan.setLevel(req.level());
        if (req.maxUses() != null) plan.setMaxUses(req.maxUses());
        return repo.save(plan);
    }

    public SubscriptionPlan update(String id, SubscriptionPlanDto.UpdateRequest req) {
        SubscriptionPlan plan = findById(id);
        if (req.name() != null && !req.name().isBlank()) plan.setName(req.name().trim());
        if (req.maxProducts() != null) plan.setMaxProducts(req.maxProducts());
        if (req.price() != null) plan.setPrice(req.price());
        if (req.currency() != null && !req.currency().isBlank()) plan.setCurrency(req.currency());
        if (req.billingPeriod() != null) plan.setBillingPeriod(BillingPeriod.valueOf(req.billingPeriod()));
        if (req.customDays() != null) plan.setCustomDays(req.customDays());
        if (req.features() != null) plan.setFeatures(req.features());
        if (req.details() != null) plan.setDetails(req.details());
        if (req.level() != null) plan.setLevel(req.level().isBlank() ? null : req.level());
        if (req.maxUses() != null) plan.setMaxUses(req.maxUses());
        return repo.save(plan);
    }

    public void delete(String id) {
        if (!repo.existsById(id)) throw new EntityNotFoundException("Subscription plan not found: " + id);
        repo.deleteById(id);
    }

    public List<String> findNamesByLevel(String level) {
        return repo.findAll().stream()
                .filter(p -> level.equals(p.getLevel()))
                .map(SubscriptionPlan::getName)
                .toList();
    }
}
