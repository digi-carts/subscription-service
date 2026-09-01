package com.digicart.subscription.dto;

import java.util.Map;

public class SubscriptionPlanDto {

    public record CreateRequest(
            String name,
            Integer maxProducts,
            Double price,
            String currency,
            String billingPeriod,
            Integer customDays,
            Map<String, Object> features,
            String details,
            String level,
            Integer maxUses
    ) {}

    public record UpdateRequest(
            String name,
            Integer maxProducts,
            Double price,
            String currency,
            String billingPeriod,
            Integer customDays,
            Map<String, Object> features,
            String details,
            String level,
            Integer maxUses
    ) {}
}
