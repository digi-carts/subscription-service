package com.digicart.subscription.dto;

import java.time.Instant;

public class MerchantSubscriptionDto {

    public record AssignRequest(
            String merchantEmail,
            String planId,
            Instant renewsAt,
            Integer availableDays
    ) {}
}
